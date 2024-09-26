package com.hongshang.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 动态获取spring中bean的工具类
 *
 * @author kobe
 * @date 2021/12/19
 */
@Component
public class BaseHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 服务器启动，Spring容器初始化时，当加载了当前类为bean组件后，
     * 将会调用下面方法注入ApplicationContext实例
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        BaseHolder.applicationContext = arg0;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 用bean组件的name来获取bean
     *
     * @param beanName
     * @return bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 用类来获取bean
     *
     * @param c Class
     * @return bean
     */
    public static <T> T getBean(Class<T> c) {
        return (T) applicationContext.getBean(c);
    }
}
