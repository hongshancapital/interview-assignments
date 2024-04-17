package com.domain.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.domain.utils.CacheUtils;
import com.domain.utils.web.interceptor.RateLimiterInterceptor;
import com.domain.utils.web.interceptor.TokenInterceptor;
import com.domain.utils.web.serializer.ToDateSerializer;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * spring mvc 全局配置
 *    1、序列化/反序列化
 *    2、鉴权拦截器
 *    3、跨域注册器
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {


    /**
     *  序列化、反序列化
     * @param
     * @return
     * @throws
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        StringHttpMessageConverter stringConverter=new StringHttpMessageConverter();
        List<MediaType> string_medias = new ArrayList<>();
        string_medias.add(MediaType.APPLICATION_JSON);
        stringConverter.setSupportedMediaTypes(string_medias);
        stringConverter.setDefaultCharset(StandardCharsets.UTF_8);

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Date.class, ToDateSerializer.instance);
        serializeConfig.put(Timestamp.class, ToDateSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.SortField,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteBigDecimalAsPlain,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        MediaType jsonUtf8 = MediaType.APPLICATION_JSON;
        List<MediaType> medias = new ArrayList<>();
        medias.add(jsonUtf8);
        fastConverter.setSupportedMediaTypes(medias);
        fastConverter.setDefaultCharset(StandardCharsets.UTF_8);

        return new HttpMessageConverters(fastConverter);
    }

    /**
     *  安全拦截器
     * @param  config  全局配置
     * @return TokenInterceptor  拦截器
     */
    @Bean
    public TokenInterceptor createTokenInterceptor(GlobalParametersConfig config){
        return  new TokenInterceptor(config);
    }
    /**
     * 全局动态参数
     * @return GlobalParametersConfig 全局配置
     */
    @Bean
    public GlobalParametersConfig createGlobalParametersConfig(){
        return  new GlobalParametersConfig();
    }
    /**
     * 限流拦截器
     * @param
     * @return  RateLimiterInterceptor 拦截器
     */
    @Bean
    public RateLimiterInterceptor createRateLimiterInterceptor(CacheUtils cacheUtils){
        return  new RateLimiterInterceptor(cacheUtils);
    }
    /**
     * 限流缓存
     * @return GlobalParametersConfig 全局配置
     */
    @Bean
    public CacheUtils createRateLimiterCache(){
        return  new CacheUtils();
    }
    /**
     * MVC配置
     * @param
     * @return
     * @throws
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter(){
        return new WebMvcConfigurer(){

            /**
             * 注册拦截器
             * @param
             * @return
             * @throws
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(createTokenInterceptor(createGlobalParametersConfig())).addPathPatterns("/**");
                registry.addInterceptor(createRateLimiterInterceptor(createRateLimiterCache())).addPathPatterns("/**");
            }
            /**
             * 跨域注册器
             * @param
             * @return
             * @throws
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowCredentials(false).maxAge(7200);
            }
        };
    }



}
