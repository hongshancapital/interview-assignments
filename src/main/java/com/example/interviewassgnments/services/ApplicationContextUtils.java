/**
 * this is a test project
 */

package com.example.interviewassgnments.services;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @Auther: maple
 * @Date: 2022/1/19 10:05
 * @Description: com.example.interviewassgnments.services
 * @version: 1.0
 */
@Component
public class ApplicationContextUtils  implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 通过属性名获取对象的方法
     *
     * @param id String
     * @return Object
     * */
    public static Object getById(String id) {
        Object bean = applicationContext.getBean(id);
        return bean;
    }

    /**
     * 通过属性类获取对象的方法
     *
     * @param clazz Class
     * @return Object
     * */
    public static Object getByClass(Class clazz) {
        Object bean = applicationContext.getBean(clazz);
        return bean;
    }

    /**
     * 通过属性类获取对象的方法
     *
     * @param id String
     * @param clazz Class
     * @return Object
     * */
    public static Object getByNameAndClass(String id, Class clazz) {
        Object bean = applicationContext.getBean(id, clazz);
        return bean;
    }


}