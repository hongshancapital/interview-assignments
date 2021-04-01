package com.sequoiacap.utils;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

/**
 * 用于获取当前应用的ApplicationContext
 *
 * 须要在IoC容器创建该对象
 */
public class ApplicationContextHolder
	implements ApplicationContextAware, ServletContextAware
{
    protected static ApplicationContext applicationContext;
    protected static ServletContext servletContext;
    protected static ApplicationContextHolder holder;

    @Autowired
    private ConfigurableBeanFactory configurableBeanFactory;
    
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
    	ApplicationContextHolder.holder = this;
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public void setServletContext(ServletContext context)
    {
        servletContext = context;
    }

    public static ServletContext getServletContext()
    {
        return servletContext;
    }

    public static Object getBean(String name)
        throws BeansException
    {
        if (applicationContext == null)
            return null;

        return applicationContext.getBean(name);
    }

    public static <T>
        T getBean(String name, Class<T> requiredType)
        throws BeansException
    {
        if (applicationContext == null)
            return null;

        return applicationContext.getBean(name, requiredType);
    }
    
    public static <T>
        T getBean(Class<T> requiredType)
        throws BeansException
    {
        if (applicationContext == null)
            return null;

        if (requiredType.isInstance(holder.configurableBeanFactory))
        {
        	return(T) holder.configurableBeanFactory;
        }

        return applicationContext.getBean(requiredType);
    }

    public static <T> T autowire(Class<T> beanClass)
	{
    	return(T) applicationContext.getAutowireCapableBeanFactory().autowire(
			beanClass, AutowireCapableBeanFactory.AUTOWIRE_NO, true);
	}

    public static <T> T autowire(T bean)
    {
    	applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    	
    	return bean;
    }

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public ConfigurableBeanFactory getConfigurableBeanFactory() {
		return configurableBeanFactory;
	}

	public void setConfigurableBeanFactory(ConfigurableBeanFactory configurableBeanFactory) {
		this.configurableBeanFactory = configurableBeanFactory;
	}
}
