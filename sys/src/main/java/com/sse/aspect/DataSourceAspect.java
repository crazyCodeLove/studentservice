package com.sse.aspect;

import com.sse.annotation.MultiDataSource;
import com.sse.config.datasource.DataSourceConfig;
import com.sse.config.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-03-30 22:33
 */

@Aspect
@Configuration
@Slf4j
public class DataSourceAspect {

    //切入点，mapper 中所有注解方法
    @Pointcut("execution(* com.sse..mapper..*.*(..))")
    public void mapperAspect() {
    }

    @Around("mapperAspect()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MultiDataSource ds = method.getAnnotation(MultiDataSource.class);
        if (ds != null) {
            DynamicDataSource.setDataSourceName(ds.value());
        } else {
            DynamicDataSource.setDataSourceName(DataSourceConfig.DEFAULT_DATASOURCE_NAME);
        }
        try {
            joinPoint.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }

}
