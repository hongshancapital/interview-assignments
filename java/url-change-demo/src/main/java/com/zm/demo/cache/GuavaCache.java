package com.zm.demo.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;

/**
 * @ClassName MyCache
 * @Description TODO
 * @Author zhaomin
 * @Date 2021/10/29 17:25
 **/
public class GuavaCache<K, V> implements Cache<K, V>{

    com.google.common.cache.Cache<K, V> cache;

    public GuavaCache(int maximumSize) {
        cache = CacheBuilder.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public void put(K k, V v) {
        cache.put(k, v);
    }

    @Override
    public V get(K k) {
        return cache.getIfPresent(k);
    }


}
