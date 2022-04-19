package com.mortimer.shortenurl.config;

import com.mortimer.shortenurl.component.cache.LocalMemoryUrlMappingCache;
import com.mortimer.shortenurl.component.cache.UrlMappingCache;
import com.mortimer.shortenurl.component.sequence.SequenceGenerator;
import com.mortimer.shortenurl.component.sequence.SimpleSequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public SequenceGenerator sequenceGenerator(ConfigProperties configProperties) {
        return new SimpleSequenceGenerator(configProperties.getSimpleGeneratorInitValue());
    }

    /**
     * shortUrl->longUrl缓存，其中shortUrl长度控制在500字符以内
     *
     * @param configProperties  配置
     * @return  UrlMappingCache对象
     */
    @Bean
    public UrlMappingCache shortUrlLongUrlMapping(ConfigProperties configProperties) {
        return new LocalMemoryUrlMappingCache(configProperties.getShortUrlLongUrlCacheMaxSize());
    }

    /**
     * shortUrl->longUrl缓存，其中shortUrl长度大于500字符
     *
     * @param configProperties  配置
     * @return  UrlMappingCache对象
     */
    @Bean
    public UrlMappingCache shortUrlTooLongUrlMapping(ConfigProperties configProperties) {
        return new LocalMemoryUrlMappingCache(configProperties.getShortUrlTooLongUrlCacheMaxSize());
    }

    /**
     * longUrl->shortUrl缓存
     *
     * @param configProperties  配置
     * @return  UrlMappingCache对象
     */
    @Bean
    public UrlMappingCache longUrlShortUrlMapping(ConfigProperties configProperties) {
        return new LocalMemoryUrlMappingCache(configProperties.getLongUrlShortUrlCacheMaxSize(),
                configProperties.getLongUrlShortUrlCacheExpireTime());
    }
}
