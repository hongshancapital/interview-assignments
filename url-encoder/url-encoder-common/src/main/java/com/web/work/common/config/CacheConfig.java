package com.web.work.common.config;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import com.web.work.common.constants.CacheConstant;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * the config of cache
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 8:42 PM
 */
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = Lists.newArrayList(new CaffeineCache(CacheConstant.CACHE_10SECS,
            Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build()));
        cacheManager.setCaches(caches);
        return cacheManager;
    }

}
