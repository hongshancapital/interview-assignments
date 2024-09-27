package com.scdt.interview.leon.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程安全的过期淘汰cache
 *
 * @author leon
 * @since 2021/12/08
 */
@Slf4j
public class OverdueCache<K, V> {
    //缓存的最大容量
    private final int maxCapacity;

    //缓存过期时间（毫秒）
    private final long expireTime;

    //缓存实现
    private final ConcurrentHashMap<K, V> cacheMap;

    //读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();

    //定时器
    private final ScheduledExecutorService scheduledExecutorService;

    public OverdueCache(int maxCapacity, long expireTime) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("Illegal max capacity: " + maxCapacity);
        }
        this.maxCapacity = maxCapacity;
        this.expireTime = expireTime;
        cacheMap = new ConcurrentHashMap<>(maxCapacity);
        scheduledExecutorService = Executors.newScheduledThreadPool(7);
    }

    public void put(K key, V value) {
        // 加写锁
        writeLock.lock();
        try {
            //是否超出缓存容量，超出的话抛出异常
            if (cacheMap.size() == maxCapacity) {
                log.error("超出系统最大容量");
                throw new IndexOutOfBoundsException("容量超限，请稍后重试");
            }

            //缓存键值对
            cacheMap.put(key, value);
            //添加过期删除线程
            removeAfterExpireTime(key);
            //return value;
        } finally {
            writeLock.unlock();
        }
    }

    public V get(K key) {
        return cacheMap.get(key);
    }

    private void removeAfterExpireTime(K key) {
        scheduledExecutorService.schedule(() -> {
            //过期后清除该键值对
            cacheMap.remove(key);
        }, expireTime, TimeUnit.MILLISECONDS);
    }

    public int size() {
        return cacheMap.size();
    }

}
