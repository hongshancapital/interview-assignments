package com.scdt.shorturl.lrucache;



public class CacheElement<K,V> {
    private final K key;
    private final V value;

    public CacheElement(K key, V value) {
        this.value = value;
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

}