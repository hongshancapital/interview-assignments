package com.scdt.domain.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.scdt.domain.properties.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Resource
    private CacheProperties cacheProperties;

    @Bean
    public Cache<String, String> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(cacheProperties.getTtl(), TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(cacheProperties.getInitSize())
                // 缓存的最大条数
                .maximumSize(cacheProperties.getMaxSize())
                .build();
    }
}
