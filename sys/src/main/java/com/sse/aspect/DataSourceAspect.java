package com.sse.aspect;

import com.sse.annotation.MultiDataSource;
import com.sse.config.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-03-30 22:33
 */

@Aspect
@Configuration
@Slf4j
@Order(1) //注意：需要使用@Order(1) 注解，保证数据源的切换在数据源的获取之前。
public class DataSourceAspect {

    //切入点，mapper 中所有注解方法
    @Pointcut("execution(* com.sse.service..*.*(..))")
    public void mapperAspect() {
    }

    @Around("mapperAspect()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MultiDataSource ds = method.getAnnotation(MultiDataSource.class);
        if (ds != null) {
            DynamicDataSource.setDataSourceName(ds.value());
            log.info("after change, datasource name: {}", DynamicDataSource.getDataSourceName());
        }
        try {
            joinPoint.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }

}
