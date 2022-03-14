/**
 * this is a test project
 */

package com.example.interviewassgnments.configs;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.JSONPResponseBodyAdvice;
import com.example.interviewassgnments.interceptor.ResponseResultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 配制拦截器及FastJson
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:59
 * @Description: com.example.interviewassgnments.configs
 * @version: 1.0
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    // SpringMVC 需要手动添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ResponseResultInterceptor interceptor = new ResponseResultInterceptor();
        registry.addInterceptor(interceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 自定义配置
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteMapNullValue);

        // 处理中文乱码
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(config);

        converters.add(0, fastConverter);
    }

    @Bean
    public JSONPResponseBodyAdvice jsonpResponseBodyAdvice() {
        return new JSONPResponseBodyAdvice();
    }
}
