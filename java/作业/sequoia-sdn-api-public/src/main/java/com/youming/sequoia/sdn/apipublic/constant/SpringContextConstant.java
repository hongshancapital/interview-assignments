package com.youming.sequoia.sdn.apipublic.constant;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用来获取spring应用上下文的类
 */
public class SpringContextConstant implements ApplicationContextAware {

    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub
        if (SpringContextConstant.applicationContext == null) {
            SpringContextConstant.applicationContext = applicationContext;
        }
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return SpringContextConstant.applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        // TODO Auto-generated method stub
        return SpringContextConstant.applicationContext.getBean(requiredType);
    }
}
