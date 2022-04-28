package com.diode.interview.domain.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Slf4j
@Component
public class MyBeanFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (this) {
            if (MyBeanFactory.applicationContext == null) {
                MyBeanFactory.applicationContext = applicationContext;
            }
        }
    }

    public static <T> T createEmptyBean(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            log.error("Spring get bean error.class:" + clazz, e);
        }
        return null;
    }
}
