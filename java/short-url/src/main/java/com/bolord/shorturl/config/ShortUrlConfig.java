package com.bolord.shorturl.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bolord.shorturl.generator.IdGenerator;
import com.bolord.shorturl.generator.SimpleIdGenerator;
import com.bolord.shorturl.storage.SimpleUrlMappingStorage;
import com.bolord.shorturl.storage.UrlMappingStorage;

@Configuration
public class ShortUrlConfig {

    @Resource
    private ShortUrlProperties shortUrlProperties;

    @Bean
    public UrlMappingStorage urlMappingStorage() {
        return new SimpleUrlMappingStorage(shortUrlProperties);
    }

    @Bean
    public IdGenerator idGenerator() {
        return new SimpleIdGenerator();
    }

}
