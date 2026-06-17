package com.david.urlconverter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * web服务本地缓存配置
 */
@Configuration
public class LocalCacheConfig {

    @Value("${caffeine.config.expireAfterAccessMinutes}")
    private Long expireAfterAccess;
    @Value("${caffeine.config.initSize}")
    private Integer initSize;
    @Value("${caffeine.config.maxSize}")
    private Long maxSize;

    @Bean
    public Cache<String, String> caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(expireAfterAccess, TimeUnit.MINUTES)
                .initialCapacity(initSize)
                .maximumSize(maxSize)
                .build();
    }

}
