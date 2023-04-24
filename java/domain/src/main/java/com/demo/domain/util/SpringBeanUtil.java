package com.demo.domain.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ClassName: SpringUtils
 * date: 2019/4/19 15:26
 * Description: Spring-bean上下文获取和bean获取
 * @author fanzj@bonree.com
 * @since JDK 1.8
 */
@Component("springUtil")
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * Title:
     * Description:  获取applicationContext
     *
     * @return
     * @date 2021/7/28-4:53 下午
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Title:
     * Description: 通过name,以及Clazz返回指定的Bean
     *
     * @return
     * @date 2021/7/28-4:53 下午
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
