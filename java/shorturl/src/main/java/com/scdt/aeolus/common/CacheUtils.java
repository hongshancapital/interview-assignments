package com.scdt.aeolus.common;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

/**
 * ehCache工具类
 */
@Component
public class CacheUtils {

    private EhCacheCacheManager getEhCacheManager() {
        CacheManager cacheManager = CacheManager.getCacheManager("myCache");
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(cacheManager);
        return ehCacheCacheManager;
    }

    /**
     * 键值存入cache
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        Cache cache = getEhCacheManager().getCache("shortUrl");
        cache.put(key, value);
    }

    /**
     * 获得已知cache的键值对
     * @param key    键
     * @return
     */
    public String get(String key) {
        Cache cache = getEhCacheManager().getCache("shortUrl");
        String value = cache.get(key, String.class);
        if (value == null) {
            return null;
        }
        return value;
    }
}