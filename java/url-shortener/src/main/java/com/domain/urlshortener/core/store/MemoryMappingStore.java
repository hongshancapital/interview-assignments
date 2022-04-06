package com.domain.urlshortener.core.store;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @Description: 缓存式内存存储
 * @author: rocky.hu
 * @date: 2022/4/5 23:07
 */
public class MemoryMappingStore implements MappingStore {

    private Cache<String, String> cache;

    public MemoryMappingStore(Long maximumSize) {
        cache =  Caffeine.newBuilder()
                .initialCapacity(10000)
                .maximumSize(maximumSize)
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
