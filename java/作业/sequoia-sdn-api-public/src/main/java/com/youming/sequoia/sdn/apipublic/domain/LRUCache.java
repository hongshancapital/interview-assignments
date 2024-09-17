package com.youming.sequoia.sdn.apipublic.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU缓存，如果超过限定的数量，最先写入的会被移除
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private int maxCacheSize = 10000;

    
    public LRUCache(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > maxCacheSize;
    }

}
