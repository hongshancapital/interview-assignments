package com.assignment.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * Lrucache
 * @author mrdiyewu@gmail.com
 * @date 2021/10/11 17:41
 */
public class LRUCache<K, V> {

    private final int maxSize;

    private ConcurrentHashMap<K, V> map;

    private ConcurrentLinkedQueue<K> queue;

    public LRUCache(final int maxSize) {
        this.maxSize = maxSize;
        map = new ConcurrentHashMap<K, V>(maxSize);
        queue = new ConcurrentLinkedQueue<K>();
    }

    public void put(final K key, final V value) {
        if (map.containsKey(key)) {
            // remove the key from the FIFO queue
            queue.remove(key);
        }

        while (queue.size() >= maxSize) {
            K oldestKey = queue.poll();
            if (null != oldestKey) {
                map.remove(oldestKey);
            }
        }
        queue.add(key);
        map.put(key, value);
    }

    public V get(final K key) {

        if (map.containsKey(key)) {
            queue.remove(key);
            queue.add(key);
        }
        return map.get(key);
    }
}
