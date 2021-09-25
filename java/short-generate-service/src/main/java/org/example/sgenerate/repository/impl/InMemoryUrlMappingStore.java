package org.example.sgenerate.repository.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.example.sgenerate.model.UrlMappingInfo;
import org.example.sgenerate.repository.UrlMappingStore;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 内存存储链接映射信息
 * 内存溢出:
 * 1. 惰性删除已过期缓存
 * 2. all-key-lru 最少使用内存淘汰机制
 *
 * @author liuyadu
 */
@Repository
public class InMemoryUrlMappingStore implements UrlMappingStore {

    Cache<String, UrlMappingInfo> cache = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            //设置缓存容器的初始容量为10
            .initialCapacity(10)
            //设置缓存最大容量，超过最大容量之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(100000)
            //设置缓存的移除通知
            .removalListener(notification -> {
                System.out.println(notification.getKey() + " 被移除,原因:" + notification.getCause());
            }).build();

    @Override
    public void storeUrl(UrlMappingInfo info) {
        cache.put(info.getId(), info);
    }

    @Override
    public void removeUrl(String id) {
        cache.invalidate(id);
    }

    @Override
    public UrlMappingInfo getUrl(String id) {
        UrlMappingInfo info = cache.getIfPresent(id);
        // 使用惰性删除
        if (info != null && info.isExpired()) {
            cache.invalidate(id);
            return null;
        }
        return info;
    }
}
