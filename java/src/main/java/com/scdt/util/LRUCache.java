package com.scdt.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU 缓存淘汰机制:即Least Recently Used 最近最少使用
 * 从近期访问最少到近期访问最多的顺序（访问顺序）。这种映射很适合构建 LRU 缓存
 *
 *  * @Author: lenovo
 *  * @since: 2021-12-15
 */
public class LRUCache<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private LinkedHashMap<K, V> map;
    private int cacheSize;
    public LRUCache(){
        this(100);
    }
    // 构造函数：cacheSize 表示缓存的大小
    public LRUCache(int cacheSize) {
        this.cacheSize = cacheSize;
        map = new LinkedHashMap<K, V>(this.cacheSize, LOAD_FACTOR, true) {//匿名子类
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {//重写removeEldestEntry方法
                return size() > LRUCache.this.cacheSize;
            }
        };
    }
 
 
    public synchronized V get(K key) {
        return map.get(key);
    }
 
    public synchronized void put(K key, V value) {
        map.put(key, value);
    }
 
    public synchronized void clear() {
        map.clear();
    }
 
    public synchronized int size() {
        return map.size();
    }

    public synchronized boolean containsKey(String key) {
        return map.containsKey(key);
    }
 
    public synchronized Collection<Map.Entry<K, V>> getAll() {
        return new ArrayList<>(map.entrySet());
    }
 

 
}