package com.scdt.shortlink.core.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于guava实现的缓存方案
 *
 * @Author tzf
 * @Date 2022/4/29
 */
@Component
public class GuavaCache implements ICache {
    /**
     * 初始化缓存对象
     * 二级缓存不用太大，留10000长度的足以
     * 失效时长可根据实际需求，在这里我保留10秒
     */
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
        .maximumSize(10000)
        .expireAfterWrite(10, TimeUnit.SECONDS)
        .build();

    /**
     * 获取缓存值
     *
     * @param key 存储key
     * @return
     */
    @Override
    public String get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * 按照kv结构放入缓存
     *
     * @param key     键
     * @param value   值
     * @return
     */
    @Override
    public void put(String key, String value) {
        cache.put(key, value);
    }
}
