package com.xwt.config.swagger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.StringUtils;

public class JsonSerializerRewriteProcessor implements BeanPostProcessor {

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    private static final String targetBeanName = "jsonSerializerRewrite";
    private static final String sourceBeanName = "jsonSerializer";

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.endsWithIgnoreCase(beanName, targetBeanName)) {
            boolean containsBean = defaultListableBeanFactory.containsBean(sourceBeanName);
            if (containsBean) {
                //移除bean的定义和实例
                defaultListableBeanFactory.removeBeanDefinition(sourceBeanName);
            }
            //注册新的bean定义和实例
            defaultListableBeanFactory.registerBeanDefinition(sourceBeanName, BeanDefinitionBuilder.genericBeanDefinition(JsonSerializerRewrite.class).getBeanDefinition());
            defaultListableBeanFactory.registerSingleton(targetBeanName, bean);
        }
        return bean;
    }

}
