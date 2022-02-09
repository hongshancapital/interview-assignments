package com.scdt.cache.lru;

import com.scdt.cache.store.CacheStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

/**
 *
 * @param <K> key
 * @param <V> value
 */
class CacheSegment<K extends Serializable, V extends Serializable> {
    private Map<K, LRUCacheEntry<K, V>> map = new HashMap<>();
    private LRUCacheEntry<K, V> head, tail;
    /**
     * 判断在什么情况下， 剔除到二级缓存,
     * 不进行内存数据剔除
     */
    private Predicate isEvictPredicate = o -> false;
    /**
     * 支持剔除到二级缓存（可以是文件、分布式缓存等）
     */
    private final List<CacheStore<K, V>> evictStores = new ArrayList<>();
    /**
     * 支持回写，进行数据持久化（DB）
     */
    private final List<CacheStore<K, V>> behindStores = new ArrayList<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();


    public CacheSegment() {}

    public CacheSegment(Predicate isEvictPredicate, List<CacheStore<K, V>> evictStores, List<CacheStore<K, V>> behindStores) {
        this.isEvictPredicate = isEvictPredicate;
        this.evictStores.addAll(evictStores);
        this.behindStores.addAll(behindStores);
    }

    public void put(K key, V value) {
        writeLock.lock();
        try {
            if (map.containsKey(key)) {
                LRUCacheEntry<K, V> lruCacheEntry = map.get(key);
                lruCacheEntry.value = value;
                removeNode(lruCacheEntry);
                offerNode(lruCacheEntry);
            } else {
                if (isEvictPredicate.test(this)) {
                    System.out.println("=============================");
                    map.remove(head.key);
                    LRUCacheEntry temp = head;
                    removeNode(head);
                    for (CacheStore store : evictStores) {
                        try {
                            store.store(temp);
                        } catch (Exception e) {
//                            log.error("剔除对象到二级缓存异常", e);
                        }
                    }
                }
                LRUCacheEntry<K, V> lruCacheEntry = new LRUCacheEntry<>(key, value);
                offerNode(lruCacheEntry);
                map.put(key, lruCacheEntry);
                for (CacheStore store : behindStores) {
                    try {
                        store.store(lruCacheEntry);
                    } catch (Exception e) {

                    }
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    public V get(K key) {
        writeLock.lock();
        try {
            LRUCacheEntry<K, V> lruCacheEntry = map.get(key);
            if (lruCacheEntry != null) {
                removeNode(lruCacheEntry);
                offerNode(lruCacheEntry);
            } else {
                lruCacheEntry = activateEntry(key);
            }
            return lruCacheEntry == null ? null : lruCacheEntry.value;
        } finally {
            writeLock.unlock();
        }
    }

    public int size() {
        readLock.lock();
        try {
            return map.size();
        } finally {
            readLock.unlock();
        }
    }

    private LRUCacheEntry<K, V> activateEntry(K key) {
        LRUCacheEntry<K, V> lruCacheEntry = null;
        for (CacheStore<K, V> store : evictStores) {
            try {
                lruCacheEntry = (LRUCacheEntry<K, V>) store.remove(key);
            } catch (Exception e) {
            }
            if (lruCacheEntry != null) {
                return lruCacheEntry;
            }
        }

        for (CacheStore<K, V> store : behindStores) {
            try {
                lruCacheEntry = (LRUCacheEntry<K, V>) store.load(key);
            } catch (Exception e) {
            }
            if (lruCacheEntry != null) {
                return lruCacheEntry;
            }
        }
        return lruCacheEntry;
    }

    private void removeNode(LRUCacheEntry<K, V> lruCacheEntry) {
        if (lruCacheEntry == null) return;

        if (lruCacheEntry.prev != null) {
            lruCacheEntry.prev.next = lruCacheEntry.next;
        } else {
            head = lruCacheEntry.next;
        }

        if (lruCacheEntry.next != null) {
            lruCacheEntry.next.prev = lruCacheEntry.prev;
        } else {
            tail = lruCacheEntry.prev;
        }
    }

    private void offerNode(LRUCacheEntry<K, V> lruCacheEntry) {
        if (lruCacheEntry == null) return;

        if (head == null) {
            head = tail = lruCacheEntry;
        } else {
            tail.next = lruCacheEntry;
            lruCacheEntry.prev = tail;
            lruCacheEntry.next = null;
            tail = lruCacheEntry;
        }
    }
}