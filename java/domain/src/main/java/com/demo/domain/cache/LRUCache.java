/*
package com.demo.domain.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

*/
/**
 * @Author fanzj
 * @Date 2022/4/2 15:29
 * @Version 3.0
 * @Description
 *//*

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    */
/**
     *
     *//*

    private static final long serialVersionUID = -2332322333223323223L;
    */
/**
     * 最大容量
     **//*

    private int maxCapacity;
    */
/**
     * 扩容因子
     **//*

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    */
/** 初始化容量 **//*

    private static final int INITIAL_CAPACITY = 16;

    private final Lock lock = new ReentrantLock();

    public LRUCache(int maxCapacity) {
        this(maxCapacity, INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true);
    }

    public LRUCache(int maxCapacity, int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public V get(Object key) {
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        lock.lock();
        try {
            return super.remove(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxCapacity;
    }

    @Override
    public boolean containsValue(Object value) {
        lock.lock();
        try {
            return super.containsValue(value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        lock.lock();
        try {
            return super.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            return super.size();
        } finally {
            lock.unlock();
        }
    }
}


*/
