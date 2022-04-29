package com.example.tinyurl.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Bean工具类
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取java bean
     *
     * @param clazz 类名
     * @param <T> 泛型
     * @return java bean
     */
    public static <T> T getBean(Class<T> clazz){
        return SpringBeanUtil.applicationContext.getBean(clazz);
    }
}
