package com.liujunpei.shorturl.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 刘俊佩
 * @date 2022/1/26 下午4:31
 */
@Slf4j
public class UrlCache {
    private static final Cache<String, String> cache = CacheBuilder.newBuilder()
            //设置并发级别为4，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(4)
            //设置缓存容器的初始容量为100
            .initialCapacity(100)
            //设置缓存最大容量为40000000，按jvm内存8G，每个key,value最大占用200字节空间
            .maximumSize(40000000)
            //设置写缓存后30天过期
            .expireAfterWrite(30 * 24 * 60 * 60, TimeUnit.SECONDS)
            .build();

    public static String get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * 将短域名和长域名保存到缓存中
     * @param key 短域名
     * @param value 长域名
     * @return 是否保存成功
     */
    public static synchronized boolean put(String key, String value) {
        boolean result = false;
        if (UrlCache.get(key) == null) {
            cache.put(key, value);
            result = true;
        }
        return result;
    }
}
