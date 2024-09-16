package com.eagle.shorturl.config;

import com.eagle.shorturl.intercepter.PermissionInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author eagle
 * @description
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor)
                .excludePathPatterns("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v3/**", "/swagger-ui.html/**");
    }

}
