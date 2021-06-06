package com.example.demo.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRULinkedHashMap <K,V> extends LinkedHashMap<K,V> {
    //定义缓存的容量
    private int capacity;
    private static final long serialVersionUID = 1L;
    //带参数的构造器
    public LRULinkedHashMap(int capacity){
        super(16,0.75f,true);
        this.capacity=capacity;
    }
    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest){
        return size()>capacity;
    }
}
