package com.domain.urlshortener.core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 8:02
 */
public class MemoryMappingCache implements MappingCache {

    private Cache<String, String> cache;

    public MemoryMappingCache(Long maximumSize, Integer ttl) {
        cache =  Caffeine.newBuilder()
                .initialCapacity(10000)
                .maximumSize(maximumSize)
                .expireAfterAccess(ttl, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public void put(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public String get(String key) {
        return cache.getIfPresent(key);
    }

}
