package com.sse.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZHAOPENGCHENG
 * @email
 * @date 2018-12-04 22:14
 */

@Configuration
@Aspect
@Slf4j
public class LogParamAspect {

    @Pointcut("execution(* com.sse.controller..*.* (..))")
    public void controllerPoint() {
    }

    @Around("controllerPoint()")
    public Object aroundController(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        /** 通用的请求数据 */
        log.info("url:" + request.getRequestURL());
        log.info("method:" + request.getMethod());
        log.info("query string:" + request.getQueryString());
        log.info("ip:" + request.getRemoteAddr());

        /** 处理方法数据 */
        log.info("class:" + point.getTarget().getClass().getName());
        log.info("method:" + point.getSignature().getName());
        log.info("param:", point.getArgs());


        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        log.info("cost time(ms):" + time);
        return result;
    }


}
