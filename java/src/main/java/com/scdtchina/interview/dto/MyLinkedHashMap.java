package com.scdtchina.interview.dto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义LinkedHashMap实现LRU
 */
public class MyLinkedHashMap extends LinkedHashMap<String, String> {
    private int capacity;

    public MyLinkedHashMap(int capacity) {
        super(16, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
        return size() > capacity;
    }
}
