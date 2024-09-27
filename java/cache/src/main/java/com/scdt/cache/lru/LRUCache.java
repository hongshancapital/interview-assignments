package com.scdt.cache.lru;

import com.scdt.cache.Cache;
import com.scdt.cache.store.CacheStore;
import com.scdt.cache.store.FileCacheStore;
import com.scdt.cache.utils.SystemUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 采用LRU算法对内存进行淘汰，具体什么时候淘汰，可以通过外部设置对应的策略来保证（默认：是通过 JVM的内存使用比例）
 * 为了提高方法的性能：这里参考 ConcurrentHashMap 1.8 之前版本的分段锁机制
 *
 * 重要概念：
 * 剔除： 对应不经常使用的对象， 将它剔除到二级缓存（更多的缓存）， 这里参考Swap 内存与磁盘的交换分区的思想
 * 回写： 做数据持久化使用，最终数据兜底
 *
 * @param <K>
 * @param <V>
 */
public class LRUCache<K extends Serializable, V extends Serializable>  implements Cache<K, V> {

    private CacheSegment<K, V> cacheSegments[];

    /**
     * 判断在什么情况下， 剔除到二级缓存,
     * 不进行内存数据剔除 当allocatedMemory 占最大内存比例 超过 0.75
     */
    private Predicate isEvictPredicate = one -> {
        return SystemUtils.allocatedMemoryRate() > 0.75;
    };
    /**
     * 支持剔除到二级缓存（可以是文件、分布式缓存等）
     */
    private final List<CacheStore<K, V>> evictStores = new ArrayList<>();
    /**
     * 支持回写，进行数据持久化（DB）
     */
    private final List<CacheStore<K, V>> behindStores = new ArrayList<>();

    public LRUCache() {
        evictStores.add(new FileCacheStore<>());
        int cores = Runtime.getRuntime().availableProcessors();
        int concurrency = cores < 2 ? 2 : cores;
        cacheSegments = new CacheSegment[concurrency];
        for (int index = 0; index < cacheSegments.length; index++) {
            cacheSegments[index] = new CacheSegment<>(isEvictPredicate, evictStores, behindStores);
        }
    }

    @Override
    public V get(K key) {
        return  cache(key).get(key);
    }

    @Override
    public void put(K key, V value) {
        cache(key).put(key, value);
    }

    private CacheSegment<K, V> cache(K key) {
        return cacheSegments[segmentIndex(key)];
    }

    /**
     * 当前直接参考 {@code java.util.HashMap#hash(java.lang.Object)}
     * @param key
     * @return
     */
    private int segmentIndex(K key) {
        int h = 0;
        int hashCode = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        return (cacheSegments.length - 1) & hashCode;
    }

}


