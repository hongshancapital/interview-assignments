package com.scdt.shortlink.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class LinkLRUCache implements LinkCache {
    // 缓存容量上限值，防止内存溢出
    @Value("${cache.maximum}")
    private int CACHE_MAX_SIZE;

    // 利用LinkedHashMap，实现LRU缓存，按照key的访问顺序过期
    final Map<String, String> cache = new LinkedHashMap(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > CACHE_MAX_SIZE;
        }
    };

    // 并发访问控制
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void save(String key, String url) {
        final Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            cache.put(key, url);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String getUrl(String key) {
        final Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }
}
