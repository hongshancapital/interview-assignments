package com.xg.shorturl.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程安全LRU缓存
 * @author xionggen
 */
public class ThreadSafeLRUCache {
    private Map<String, Entry> shortUrlMap = new ConcurrentHashMap<>();
    private Map<String, Entry> originalUrlMap = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<Entry> queue = new ConcurrentLinkedQueue<>();
    private int maxSize;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    @Getter
    @AllArgsConstructor
    private static class Entry {
        private String shortUrl;
        private String originalUrl;
    }

    public ThreadSafeLRUCache(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getShortUrl(String originalUrl) {
        readLock.lock();
        try {
            if (originalUrlMap.containsKey(originalUrl)) {
                Entry entry = originalUrlMap.get(originalUrl);
                queue.remove(entry);
                queue.add(entry);
                return entry.getShortUrl();
            }
        } finally {
            readLock.unlock();
        }
        return null;
    }

    public String getOriginalUrl(String shortUrl) {
        readLock.lock();
        try {
            if (shortUrlMap.containsKey(shortUrl)) {
                Entry entry = shortUrlMap.get(shortUrl);
                queue.remove(entry);
                queue.add(entry);
                return entry.getOriginalUrl();
            }
        } finally {
            readLock.unlock();
        }
        return null;
    }

    public int save(String shortUrl, String originalUrl) {
        writeLock.lock();
        try {
            if (shortUrlMap.containsKey(shortUrl) || originalUrlMap.containsKey(originalUrl)) {
                return 0;
            }
            if (shortUrlMap.size() >= maxSize) {
                Entry entry = queue.poll();
                if (entry != null) {
                    shortUrlMap.remove(entry.getShortUrl());
                    originalUrlMap.remove(entry.getOriginalUrl());
                }
            }
            Entry entry = new Entry(shortUrl, originalUrl);
            queue.add(entry);
            shortUrlMap.put(entry.getShortUrl(), entry);
            originalUrlMap.put(entry.getOriginalUrl(), entry);
            return 1;
        } finally {
            writeLock.unlock();
        }
    }
}
