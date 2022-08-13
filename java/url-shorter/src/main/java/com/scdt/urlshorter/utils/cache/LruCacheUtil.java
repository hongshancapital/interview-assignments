package com.scdt.urlshorter.utils.cache;

/**
 * LRU缓存工具类
 *
 * @author mingo
 */
@SuppressWarnings("unchecked")
public class LruCacheUtil<K, V>
{
    /**
     * 缓存分段
     */
    private final CacheSegment<K, V>[] cacheSegments;

    /**
     * 构造函数
     *
     * @param maxCapacity 最大容量
     */
    public LruCacheUtil(final int maxCapacity)
    {
        //取得CPU核数
        int cpuCores = Runtime.getRuntime().availableProcessors();
        //计算出并发数
        int concurrency = Math.max(cpuCores, 2);
        cacheSegments = new CacheSegment[concurrency];
        int segmentCapacity = maxCapacity / concurrency;
        if (maxCapacity % concurrency == 1)
        {
            segmentCapacity++;
        }
        //构建缓存的分段
        for (int index = 0; index < cacheSegments.length; index++)
        {
            cacheSegments[index] = new CacheSegment<>(segmentCapacity);
        }
    }

    /**
     * 构造函数
     *
     * @param concurrency 并发数
     * @param maxCapacity 最大容量
     */
    public LruCacheUtil(final int concurrency, final int maxCapacity)
    {
        cacheSegments = new CacheSegment[concurrency];
        int segmentCapacity = maxCapacity / concurrency;
        if (maxCapacity % concurrency == 1)
        {
            segmentCapacity++;
        }
        for (int index = 0; index < cacheSegments.length; index++)
        {
            cacheSegments[index] = new CacheSegment<>(segmentCapacity);
        }
    }

    /**
     * 通过KEY查询分段索引
     *
     * @param key 缓存KEY
     * @return 分段索引
     */
    private int segmentIndex(K key)
    {
        int hashCode = Math.abs(key.hashCode() * 31);
        return hashCode % cacheSegments.length;
    }

    /**
     * 通过缓存KEY取得缓存分段
     *
     * @param key 缓存KEY
     * @return 缓存分段
     */
    private CacheSegment<K, V> segment(K key)
    {
        return cacheSegments[segmentIndex(key)];
    }

    /**
     * 存储数据到分段中
     *
     * @param key   key
     * @param value value
     */
    public void put(K key, V value)
    {
        segment(key).put(key, value);
    }

    /**
     * 通过缓存KEY取得分段中的数据
     *
     * @param key 缓存KEY
     * @return 分段数据
     */
    public V get(K key)
    {
        return segment(key).get(key);
    }

    /**
     * 取得所有缓存分段大小
     *
     * @return 分段大小总和
     */
    public int size()
    {
        int size = 0;
        for (CacheSegment<K, V> segment : cacheSegments)
        {
            size += segment.size();
        }
        return size;
    }
}
