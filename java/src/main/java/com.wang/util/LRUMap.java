package com.wang.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * desc: 实现lru map
 * author: wang
 * date: 2021-11-04
 * */

public class LRUMap<K,V> extends LinkedHashMap<K,V> {


    private int capacity;


    public  LRUMap(int capacity){
        super(capacity,0.75f,true);
        this.capacity=capacity;
    }
    public boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size()>capacity;
    }
}
