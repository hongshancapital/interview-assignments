package com.scdt.interview.leon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置静态资源映射
 *
 * @author leon
 * @since 2021/10/26
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* swagger-ui的资源处理器 */
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 可添加多个拦截器组成一个拦截器链，addPathPatterns 用于添加拦截规则，excludePathPatterns 用户排除拦截
     * 使用spring 5.x时，静态资源也会执行自定义的拦截器，需将静态资源和公开资源排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* 注册拦截器 */
    }

    @Override
    public Validator getValidator() {
        return validator();
    }
}
