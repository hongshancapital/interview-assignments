package com.scdt.cache;


/**
 * 缓存的接
 * @param <K>
 * @param <V>
 */
public interface Cache<K, V> {
    V get(K key);

    void put(K key, V value);
}
