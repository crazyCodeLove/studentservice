package com.sse.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.exception.ExceptionCodeEnum;
import com.sse.exception.RTException;
import com.sse.model.RequestParamHolder;
import com.sse.model.ResponseResultHolder;
import com.sse.model.log.LogInfo;
import com.sse.model.param.RequestParamBase;
import com.sse.service.log.LogService;
import com.sse.util.IpUtil;
import com.sse.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * author ZHAOPENGCHENG <br/>
 * date 2018-12-04 22:14
 */

@Configuration
@Aspect
@Slf4j
public class LogParamAspect {

    private LogService logService;
    private ObjectMapper objectMapper;

    @Autowired
    public LogParamAspect(LogService logService) {
        this.logService = logService;
        objectMapper = new ObjectMapper();
    }

    /**
     * 只记录请求的参数
     */
    @Pointcut("execution(* com.sse.controller..*.* (..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerPoint() {
    }

    @Around("controllerPoint()")
    public Object aroundController(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        long endTime;
        LogInfo logInfo = new LogInfo();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        fillLogInfo(request, point, logInfo);
        ResponseResultHolder result;
        try {
            // 对参数进行统一校验
            validParamInAsp(point.getArgs());
            result = (ResponseResultHolder) point.proceed();
            logInfo.setResult(getObjStr(result));
            logInfo.setResponseStatus(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getNote());
        } catch (RTException e) {
            logInfo.setResponseStatus(e.getCode(), e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            logInfo.setResponseStatus(ExceptionCodeEnum.RUNTIME_EXCEPTION.getCode(), e.getMessage());
            throw e;
        } finally {
            endTime = System.currentTimeMillis();
            logInfo.setResponseTime(new Date(endTime), endTime - startTime);
            logService.save(logInfo);
        }
        result.setResponseTime(new Date(endTime), endTime - startTime);
        result.setResponseStatus(logInfo.getCode(), logInfo.getMessage());
        return result;
    }

    /**
     * 对参数使用 hibernate validator 进行校验
     *
     * @param params 请求入参
     */
    public void validParamInAsp(Object[] params) {
        if (params != null) {
            for (Object obj : params) {
                if (obj instanceof RequestParamHolder) {
                    Object param = ((RequestParamHolder) obj).getParam();
                    if (param != null) {
                        ValidateUtil.validate(param);
                        if (param instanceof RequestParamBase) {
                            ((RequestParamBase) param).preHandle();
                            ((RequestParamBase) param).validParamInParam();
                        }
                    }
                }
            }
        }
    }

    private void fillLogInfo(HttpServletRequest request, ProceedingJoinPoint point, LogInfo logInfo) {
        logInfo.setUrl((request.getRequestURL().toString()));
        logInfo.setMethod(request.getMethod());
        logInfo.setQueryString((request.getQueryString()));
        logInfo.setIp(IpUtil.getRequestIpAddr(request));

        logInfo.setCallClass((point.getTarget().getClass().getName()));
        logInfo.setCallMethod((point.getSignature().getName()));
        String param = "";
        if (request.getParameterMap().size() > 0 && !"get".equals(request.getMethod().toLowerCase())) {
            // form 请求
            param = "paramMap:" + getObjStr(request.getParameterMap()) + "; ";
        }
        param += "Args:" + Arrays.toString(point.getArgs());
        logInfo.setParams(param);

    }

    /**
     * 获取对象的字符串表示
     *
     * @param object 一般对象
     * @return 对象的字符串表示
     */
    private String getObjStr(Object object) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取返回结果的 string 表示
     *
     * @param response 响应结果
     * @return 相应对应的字符串
     */
    private String getObjStr(ResponseResultHolder response) {
        if (response != null) {
            if (response.getResult() != null) {
                return response.getResult().toString();
            }
        }
        return null;
    }

}
