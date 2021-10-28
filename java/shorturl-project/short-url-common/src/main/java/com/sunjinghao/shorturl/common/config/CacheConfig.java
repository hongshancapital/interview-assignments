package com.sunjinghao.shorturl.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Cache配置類
 *
 * @author sunjinghao
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * 默认：最大淘汰时间
     */
    public static final int DEFAULT_MAXSIZE = 500;
    /**
     * 默认：容量 大小
     */
    public static final int DEFAULT_TTL = 60;

    /**
     * 默认：最大条数
     */
    public static final int DEFAULT_MAX_LENGTH = 60;


    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(DEFAULT_TTL, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(DEFAULT_MAXSIZE)
                // 缓存的最大条数
                .maximumSize(DEFAULT_MAX_LENGTH)
                .build();
    }


}