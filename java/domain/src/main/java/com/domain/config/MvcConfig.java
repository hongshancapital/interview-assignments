package com.domain.config;

import com.domain.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jib
 * @desciption
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private LogInterceptor logInterceptor;
    /**
     * 添加拦截器方法
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/index");
    }
}
