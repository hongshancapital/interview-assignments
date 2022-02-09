package com.scdt.cache;

import java.io.Serializable;

/**
 * 缓存最终的元数据， K，V
 * @param <K>
 * @param <V>
 */
public interface CacheEntry<K, V> extends Serializable {
    K getKey();

    V getValue();

    void setValue(V value);
}
