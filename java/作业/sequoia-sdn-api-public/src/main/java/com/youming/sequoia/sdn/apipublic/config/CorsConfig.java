package com.youming.sequoia.sdn.apipublic.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")        //放行哪些原始域
                //.allowCredentials(true)        //是否发送Cookie信息
                //.allowedMethods("GET","POST", "PUT", "DELETE","HEAD","OPTIONS")		//放行哪些原始域(请求方式)
                //.allowedHeaders("*")		//放行哪些原始域(头部信息)
                //.exposedHeaders("app-authc", "Header2")		//暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                ;
            }
        };
    }
}
