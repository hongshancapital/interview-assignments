package com.java.assignment.web.config;

import com.java.assignment.web.framework.CurrentLimitInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CurrentLimitInterceptor currentLimitInterceptor;

    /**
     * 将自定义的限流拦截器添加到配置中
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //此处也可以通过addPathPatterns方法添加此拦截器对部分请求路径有效，也可以通过excludePathPatterns过滤请求路径
        registry.addInterceptor(currentLimitInterceptor);
    }
}
