package com.scdt.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CacheUtil {
    private static final Cache<Object, Object> cache = CacheBuilder.newBuilder()
            //同时写缓存的线程数
            .concurrencyLevel(10)
            //初始容量
            .initialCapacity(2500)
            //最大容量
            .maximumSize(1000000)
            //缓存过期时间
            .expireAfterWrite(24 * 60 * 60, TimeUnit.SECONDS)
            .build();

    public static void put(String key, String val) {
        cache.put(key, val);
    }

    public static String get(String key) {
        Object val = cache.getIfPresent(key);
        return Objects.isNull(val) ? null : val.toString();
    }
}
