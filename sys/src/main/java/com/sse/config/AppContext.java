package com.sse.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * author pczhao <br/>
 * date 2019-01-16 19:48
 */

public class AppContext implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    /**
     * 从静态变量 appContext 中取得 Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        if (!appContext.containsBean(beanName)) {
            return null;
        }
        return (T) appContext.getBean(beanName);
    }

    /**
     * 从静态变量 appContext 中取得 Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        return (T) appContext.getBeansOfType(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (!appContext.containsBean(beanName)) {
            return null;
        }
        return appContext.getBean(beanName, clazz);
    }

    public static ServletRequestAttributes getServletRequests() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
