package com.scdt.domain.util;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Component
public class UrlCacheStorageHelper {

    @Resource
    Cache<String, String> caffeineCache;

    public String get(String key) {
        return caffeineCache.getIfPresent(key);
    }

    public void save(String key, String value) {
        caffeineCache.put(key, value);
        caffeineCache.put(value, key);
    }

    public void clear(){
        caffeineCache.cleanUp();
    }
}
