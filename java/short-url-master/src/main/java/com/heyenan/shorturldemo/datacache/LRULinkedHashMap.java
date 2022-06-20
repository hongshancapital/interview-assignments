package com.heyenan.shorturldemo.datacache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author heyenan
 * @description 实现LRU算法
 *
 * @date 2020/5/07
 */
class LRULinkedHashMap<K,V> extends LinkedHashMap<K,V> {

    private int capacity;

    public LRULinkedHashMap(int capacity) {
        super(capacity,0.75f,true);
        this.capacity = capacity;
    }
    /**
     * LRU算法实现
     *
     * @param eldest
     * @return LRU判断
     */
    @Override
    public boolean removeEldestEntry(Map.Entry<K,V> eldest) {

        return size()>capacity;
    }
}
