package com.sequoia.urllink.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 在容器启动后,也可以通过 getBean(String name) 得到对象
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  /**
   * 获取对象
   * 这里重写了bean方法，起主要作用
   *
   * @param name
   * @return Object 一个以所给名字注册的bean的实例
   * @throws BeansException
   */
  public static Object getBean(String name) throws BeansException {
    return applicationContext.getBean(name);
  }

  /**
   * 获取对象
   * 这里重写了bean方法，起主要作用
   *
   * @param className
   * @return Object 一个以所给名字注册的bean的实例
   * @throws BeansException
   */
  public static Object getBean(Class className) throws BeansException {
    return applicationContext.getBean(className);
  }

  @Autowired
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ApplicationContextUtil.applicationContext = applicationContext;
  }
}