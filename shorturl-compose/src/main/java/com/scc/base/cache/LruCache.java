package com.scc.base.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author renyunyi
 * @date 2022/4/24 5:13 PM
 * @description least recently used for cache url, this LRU cache implement based on LinkedHashMap
 **/
public class LruCache<K, V> extends AbstractCacheService<K, V> {

    private final Map<K, V> map;

    public LruCache(int capacity){
        super(capacity);
        //make capacity == initCapacity, it will not resize again
        map = new LinkedHashMap<K, V>(capacity, 0.75f, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                //when size is bigger than the capacity, remove least recently used key value
                return size() > capacity;
            }
        };
    }

    /**
     * thread safety add cache
     * @param key cache key
     * @param value cache value
     */
    @Override
    public synchronized void put(K key, V value){
        map.put(key, value);
    }

    /**
     * thread safety get cache
     * @param key cache key
     * @return value
     */
    @Override
    public synchronized V get(K key){
        return map.get(key);
    }

}
