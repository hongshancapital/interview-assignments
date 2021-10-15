package com.example.assignment.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实现最近最少使用算法的缓存，用于短域名存储
 */
public class LRUCache<K, V> {
    private final int MAX_CACHE_SIZE;
    private final float DEFAULT_LOAD_FACTORY = 0.75f;

    private Map<K, V> map;
    private Map<V, K> reverseMap;

    public LRUCache(int cacheSize) {
        MAX_CACHE_SIZE = cacheSize;
        int capacity = (int) Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTORY) + 1;
        reverseMap = new HashMap<>(capacity, DEFAULT_LOAD_FACTORY);
        map = Collections.synchronizedMap(new LinkedHashMap<K, V>(capacity, DEFAULT_LOAD_FACTORY, true) {
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                if (size() > MAX_CACHE_SIZE) {
                    reverseMap.remove(eldest.getValue());
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void put(K key, V value) {
        map.put(key, value);
        reverseMap.put(value, key);
    }

    public V get(K key) {
        return map.get(key);
    }

    public K getKeyByValue(V value) {
        return reverseMap.get(value);
    }

    public int size() {
        return map.size();
    }
}