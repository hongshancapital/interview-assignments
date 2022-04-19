package com.mortimer.shortenurl.component.cache;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * 本地URL缓存
 */
public class LocalMemoryUrlMappingCache implements UrlMappingCache {
    private Cache<String, String> urlCache;

    public LocalMemoryUrlMappingCache(long maxSize) {
        urlCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .build();
    }

    public LocalMemoryUrlMappingCache(long maxSize, int expireTime) {
        this(maxSize, expireTime, TimeUnit.MINUTES);
    }

    public LocalMemoryUrlMappingCache(long maxSize, int expireTime, TimeUnit timeUnit) {
        urlCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterAccess(expireTime, timeUnit)
                .build();
    }

    @Override
    public String get(String key) {
        return urlCache.getIfPresent(key);
    }

    @Override
    public void put(String key, String value) {
        urlCache.put(key, value);
    }
}
