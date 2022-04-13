package com.francis.urltransfer.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Francis
 * @Description: LRU缓存实现类
 * @date 2022/1/27 17:58
 */
public class LruLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private int threshold;

    public LruLinkedHashMap(int threshold) {
        super(16, 0.75f, true);
        this.threshold = threshold;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > threshold;
    }
}
