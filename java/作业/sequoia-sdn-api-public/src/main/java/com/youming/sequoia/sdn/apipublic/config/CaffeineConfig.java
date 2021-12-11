package com.youming.sequoia.sdn.apipublic.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Caffeine配置
 */
@Configuration
@EnableCaching // 使用进程级缓存
public class CaffeineConfig {

    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<CaffeineCache>();


        /*
        caches.add(new CaffeineCache("UserInfoCache", Caffeine.newBuilder().recordStats()
                .expireAfterWrite(60, TimeUnit.SECONDS) // 写后60s过期
                .maximumSize(10000) // 缓存最大数量
                .build()));
         */
        // 定义短域名登录信息的缓存,这里为了防止OOM只留了100w记录
        caches.add(new CaffeineCache("ShortUrlCache", Caffeine.newBuilder().recordStats()
                //.expireAfterAccess(180, TimeUnit.SECONDS) // 读后180s过期
                .maximumSize(1000000L) // 缓存最大数量
                .build()));
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
