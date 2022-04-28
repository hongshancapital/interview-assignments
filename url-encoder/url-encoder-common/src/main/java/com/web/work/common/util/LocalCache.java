package com.web.work.common.util;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * local cache
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/28 2:33 PM
 */
public class LocalCache {

    private final static Integer INITIAL_DELAY = 5;
    private final static Integer PERIOD = 5;

    private static LruMap<String, String> cache;
    private static ConcurrentHashMap<Long, String> expireTimeCache;

    private static final int DEFAULT_MAX_CAPACITY = 25000;

    /**
     * expire time (second)
     */
    private final Long survivalTime;

    /**
     * cache init
     *
     * @param survivalTime the survival time of entry
     */
    public LocalCache(Long survivalTime) {
        cache = new LruMap<>(10000);
        expireTimeCache = new ConcurrentHashMap<>();
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("schedule-thread-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new ExpireCheckTask(), INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
        this.survivalTime = survivalTime;
    }

    /**
     * put into cache
     */
    public void put(String key, String value) {
        long expireTime;
        if (Objects.isNull(survivalTime) || survivalTime < 0) {
            expireTime = -1L;
        } else {
            expireTime = System.currentTimeMillis() + survivalTime * 1000;
        }
        cache.put(key, value);
        expireTimeCache.put(expireTime, key);
    }

    /**
     * get value by key
     */
    public Object get(String key) {
        return cache.get(key);
    }

    /**
     * regularly scan expired keys
     */
    private static class ExpireCheckTask implements Runnable {

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            // 遍历所有缓存的过期时间
            for (Long expireTime : expireTimeCache.keySet()) {
                if (expireTime > now || expireTime < 0) {
                    continue;
                }
                // get expire key and remove from cache
                String key = expireTimeCache.get(expireTime);
                cache.remove(key);
                expireTimeCache.remove(expireTime);
            }
        }
    }

    /**
     * lru based map
     *
     * @param <K>
     * @param <V>
     */
    static class LruMap<K, V> extends LinkedHashMap<K, V> {

        private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private final Lock rLock = readWriteLock.readLock();
        private final Lock wLock = readWriteLock.writeLock();

        private static final float DEFAULT_LOAD_FACTOR = 0.75f;

        private LruMap(int initialCapacity) {
            super(initialCapacity, DEFAULT_LOAD_FACTOR);
        }

        V get(String k) {
            rLock.lock();
            try {
                return super.get(k);
            } finally {
                rLock.unlock();
            }
        }

        @Override
        public V put(K k, V v) {
            wLock.lock();
            try {
                return super.put(k, v);
            } finally {
                wLock.unlock();
            }
        }

        @Override
        public V remove(Object k) {
            wLock.lock();
            try {
                return super.remove(k);
            } finally {
                wLock.unlock();
            }
        }

        @Override
        public int size() {
            rLock.lock();
            try {
                return super.size();
            } finally {
                rLock.unlock();
            }
        }

        /**
         * rewrite removeEldestEntry method if current map's size is over default_max_capacity, then remove the eldest entry;
         *
         * @param eldest The least recently inserted entry in the map
         * @return false or true
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > DEFAULT_MAX_CAPACITY;
        }
    }
}