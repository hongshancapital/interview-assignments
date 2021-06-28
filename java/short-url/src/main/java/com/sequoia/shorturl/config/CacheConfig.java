package com.sequoia.shorturl.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;import java.util.concurrent.TimeUnit;

/***
 *
 *本地缓存
 *
 *@Author xj
 *
 *@Date 2021/6/28 01:19
 *
 *@version v1.0
 *
 */
@Slf4j
@Configuration
public class CacheConfig {

    /**
     *  shortCache key shortUrl,value originalUrl
     */
    @Bean
    public Cache<String, String> shortCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                //.expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                .expireAfterAccess(1, TimeUnit.DAYS)
                // 缓存的最大条数
                .maximumSize(500000)
                .build();
    }
    /**
     *  originalCache key originalUrl,value shortUrl
     */
    @Bean
    public Cache<String, String> originalCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                //.expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(1000)
                .expireAfterAccess(1, TimeUnit.DAYS)

                // 缓存的最大条数
                .maximumSize(500000)
                .build();
    }
}
