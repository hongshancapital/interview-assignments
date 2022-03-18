package com.yofei.shortlink.configuration;

import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//
//@Configuration
//@Import({SwaggerConfiguration.class, DataSourceConfiguration.class})
//public class ApplicationWebConfiguration extends WebMvcConfigurationSupport {
//
//
//    @Override
//    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
//        converters.add(MessageConverters.mappingJackson2HttpMessageConverter);
//        super.configureMessageConverters(converters);
//    }
//
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
////        // 解决 SWAGGER 404报错
////        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
////        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
////    }
//
//
//}
