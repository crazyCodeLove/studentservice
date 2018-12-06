package com.sse.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author ZHAOPENGCHENG
 * @email
 * @date 2018-12-04 22:14
 */

@Configuration
@Aspect
@Slf4j
public class LogParamAspect {
    ThreadLocal<StringBuilder> logHandler = new ThreadLocal<>();
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* com.sse.controller..*.* (..))")
    public void controllerPoint() {
    }

    @Around("controllerPoint()")
    public Object aroundController(ProceedingJoinPoint point) throws Throwable {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logHandler.set(new StringBuilder());
        /** 通用的请求数据 */
        logHandler.get().append("session ID:");
        logHandler.get().append(request.getSession().getId());
        logHandler.get().append("; url:");
        logHandler.get().append(request.getRequestURL());
        logHandler.get().append("; method:");
        logHandler.get().append(request.getMethod());
        logHandler.get().append("; query string:");
        logHandler.get().append(request.getQueryString());
        logHandler.get().append("; ip:");
        logHandler.get().append(IpUtil.getIpAddr(request));

        /** 处理方法数据 */
        logHandler.get().append("; class:");
        logHandler.get().append(point.getTarget().getClass().getName());
        logHandler.get().append("; method:");
        logHandler.get().append(point.getSignature().getName());
        logHandler.get().append("; param:");
        logHandler.get().append(Arrays.toString(point.getArgs()));
        log.info(logHandler.get().toString());
        logHandler.remove();

        Object result = point.proceed();

        logHandler.set(new StringBuilder());
        logHandler.get().append("session ID:");
        logHandler.get().append(request.getSession().getId());
        logHandler.get().append("; result:");
        logHandler.get().append(result);
        logHandler.get().append("; cost time(ms):");
        logHandler.get().append(System.currentTimeMillis() - startTime.get());
        startTime.remove();
        log.info(logHandler.get().toString());
        logHandler.remove();
        return result;
    }


}
