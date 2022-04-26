package com.scc.base.service;


/**
 * @author renyunyi
 * @date 2022/4/24 5:25 PM
 * @description it is a template for cache service
 */
public interface Cache<K, V>{

    /**
     * add cache key and value
     * @param key cache key
     * @param value cache value
     *
     */
    void put(K key, V value);

    /**
     * get the value by key
     * @param key cache key
     * @return cache value
     */
    V get(K key);

}
