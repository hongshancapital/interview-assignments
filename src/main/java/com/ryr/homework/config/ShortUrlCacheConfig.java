package com.ryr.homework.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ryr.homework.common.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ShortUrlCacheConfig {

    @Bean
    public LoadingCache<String, String> shortUrlCache() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(Constant.SHOR_URL_CACHE_MAX_SIZE)
                .expireAfterAccess(Constant.SHOR_URL_CACHE_EXPIRE_DAYS, TimeUnit.DAYS)
                .concurrencyLevel(Constant.SHOR_URL_CACHE_CONCURRENCY)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return "";
                    }
                });
        return cache;
    }

}
