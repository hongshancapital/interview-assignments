package com.zj.domainservice.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruLinkedHashMap<K,V> extends LinkedHashMap<K,V> {

    private int maxCapacity;

    public LruLinkedHashMap(int maxCapacity) {
        //调用LinkedHashMap的构造器，传入以下参数
        super(16, 0.75f, true);
        //传入指定的缓存最大容量
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > maxCapacity;
    }
}
