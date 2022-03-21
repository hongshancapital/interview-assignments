package com.wuaping.shortlink.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * 短域名缓存
 *
 * @author Aping
 * @since 2022/3/18 10:45
 */
@Service
public class ShortLinkCache {

    @Autowired
    private CaffeineCacheManager caffeineCacheManager;

    /**
     * 缓存读取
     * @param name 缓存名
     * @param key 键
     * @param <T> 值
     * @return 返回缓存值
     */
    public <T> T getValue(@NonNull String name, @NonNull Object key) {

        Cache cache = caffeineCacheManager.getCache(name);
        Cache.ValueWrapper wrapper = cache.get(key);
        if (wrapper != null) {
            return (T) wrapper.get();
        }
        return null;
    }

    /**
     * 保存缓存
     * @param name 缓存名
     * @param key 键
     * @param value 值
     */
    public void putValue(@NonNull String name, @NonNull Object key, @NonNull Object value) {

        caffeineCacheManager.getCache(name).put(key, value);

    }

}
