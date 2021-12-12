package com.scdt.cache.lru;

import com.scdt.cache.CacheEntry;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * LRU 缓存具体Entry
 * @param <K>
 * @param <V>
 */
public class LRUCacheEntry<K, V> implements CacheEntry<K, V> {
    final K key;
    volatile V value;

    transient volatile LRUCacheEntry<K, V> next = null;
    transient volatile LRUCacheEntry<K, V> prev = null;

    public LRUCacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        UNSAFE.putOrderedObject(this,valueOffset,value);
    }

    public LRUCacheEntry<K, V> getNext() {
        return next;
    }

    public void setNext(LRUCacheEntry<K, V> next) {
        UNSAFE.putOrderedObject(this, nextOffset, next);
    }

    public LRUCacheEntry<K, V> getPrev() {
        return prev;
    }

    public void setPrev(LRUCacheEntry<K, V> previous) {
        UNSAFE.putOrderedObject(this, prevOffset, next);
    }


    static final Unsafe UNSAFE;
    static final long valueOffset;

    static final long nextOffset;
    static final long prevOffset;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);

            Class k = LRUCacheEntry.class;
            valueOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("value"));
            nextOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("next"));
            prevOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("prev"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}