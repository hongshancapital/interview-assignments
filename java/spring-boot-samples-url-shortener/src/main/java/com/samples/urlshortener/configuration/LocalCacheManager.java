package com.samples.urlshortener.configuration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: liuqu
 * DateTime: 2022年04月17日 09时23分
 * Function: 基于guava的内存缓存类
 */
@Service
public class LocalCacheManager {


    private static LocalCacheManager ourInstance = new LocalCacheManager();

    public static LocalCacheManager getInstance() {
        return ourInstance;
    }

    private LocalCacheManager() {
    }
    private Cache<String, String> shortLongMap = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(Duration.ofHours(12))
            .weakValues()
            .build();

    public String getLongUrlByShortUrl(String key) {
        return shortLongMap.getIfPresent(key);
    }

    public void setShortLongMap(String key, String value) {
        shortLongMap.put(key, value);
    }
}
