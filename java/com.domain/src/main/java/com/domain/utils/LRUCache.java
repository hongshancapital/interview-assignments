package com.domain.utils;

import java.util.LinkedHashMap;

public class LRUCache<K,V> extends LinkedHashMap<K,V> {

    int capacity=10;

    public LRUCache(int capacity){
        super(capacity,0.75f,true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        if(size() > capacity){
            return true;
        }
        else return false;
    }
}
