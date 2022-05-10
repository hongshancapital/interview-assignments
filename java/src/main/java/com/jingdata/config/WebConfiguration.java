package com.jingdata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * web配置
 *
 * @Author
 * @Date
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    private BaseInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).excludePathPatterns("**/*.html").excludePathPatterns("**/*.ico");
    }
}
