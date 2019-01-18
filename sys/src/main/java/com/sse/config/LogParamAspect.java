package com.sse.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.exception.RTException;
import com.sse.model.RequestParamBase;
import com.sse.model.RequestParamHolder;
import com.sse.model.ResponseResultHolder;
import com.sse.model.log.LogInfo;
import com.sse.service.LogService;
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
import java.util.Date;

/**
 * author ZHAOPENGCHENG
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
            logInfo.setResult(toFixedLengthStr(getObjStr(result.getResult())));
            logInfo.setCode(200);
        } catch (RTException e) {
            logInfo.setCode(e.getCode());
            throw e;
        } catch (RuntimeException e) {
            logInfo.setCode(500);
            throw e;
        } finally {
            endTime = System.currentTimeMillis();
            logInfo.setDuration(endTime - startTime);
            logInfo.setResponseTime(new Date(endTime));
            logService.save(logInfo);
        }
        result.setResponseTime(new Date(endTime));
        result.setDuration(endTime - startTime);
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
                            ((RequestParamBase) param).validParamInParam();
                        }
                    }
                }
            }
        }
    }

    private void fillLogInfo(HttpServletRequest request, ProceedingJoinPoint point, LogInfo logInfo) {
        logInfo.setUrl(toFixedLengthStr(request.getRequestURL().toString()));
        logInfo.setMethod(request.getMethod());
        logInfo.setQueryString(toFixedLengthStr(request.getQueryString()));
        logInfo.setIp(IpUtil.getRequestIpAddr(request));

        logInfo.setCallClass(toFixedLengthStr(point.getTarget().getClass().getName()));
        logInfo.setCallMethod(toFixedLengthStr(point.getSignature().getName()));
        logInfo.setParams(toFixedLengthStr(getObjStr(request.getParameterMap())));

    }

    /**
     * 字符串长度不超过 1024 长度
     *
     * @param content 内容
     * @return 结果
     */
    private static String toFixedLengthStr(String content) {
        if (content != null && content.length() > 1024) {
            return content.substring(0, 1024);
        }
        return content;
    }

    private String getObjStr(Object object) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

}
