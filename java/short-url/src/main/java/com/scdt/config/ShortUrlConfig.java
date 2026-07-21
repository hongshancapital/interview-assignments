package com.scdt.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 应用配置类
 *
 * @author penghai
 * @date 2022/5/3
 */
@Configuration
public class ShortUrlConfig {
    // caffeine缓存空间初始容量
    @Value("${caffeine.initial-capacity}")
    private int initialCapacity;

    // caffeine缓存最大条数
    @Value("${caffeine.maximum-size}")
    private long maximumSize;

    // caffeine最后一次写入后经过固定时间过期，单位为分
    @Value("${caffeine.expire-after-write}")
    private long expireAfterWrite;

    @Bean
    public Cache<String, String> cache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(initialCapacity)
                // 缓存的最大条数
                .maximumSize(maximumSize).build();
    }
}
