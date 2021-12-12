package com.scdt.cache.store;

import com.scdt.cache.CacheEntry;

import java.io.Closeable;

public interface CacheStore<K, V> extends Closeable {
    void store(CacheEntry<K, V> entry) throws Exception;

    CacheEntry<K, V> load(K key) throws Exception;

    CacheEntry<K, V> remove(K key) throws Exception;

    void close();
}