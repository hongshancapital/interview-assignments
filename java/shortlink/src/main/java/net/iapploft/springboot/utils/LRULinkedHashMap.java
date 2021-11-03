package net.iapploft.springboot.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * LRU是Least Recently Used的缩写，即最近最少使用，是一种常用的页面置换算法，选择最近最久未使用的页面予以淘汰。
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private long maxSize;
    /**
     * 读写锁
     */
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock writeLock = readWriteLock.writeLock();
    private Lock readLock = readWriteLock.readLock();

    public LRULinkedHashMap(long maxSize) {

        // 初始大小，0.75是装载因子，true是表示按照访问时间排序
        super(100, 0.75f, true);
        //传入指定的缓存最大容量
        this.maxSize = maxSize;
    }

    /**
     * 实现LRU的关键方法，如果map里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }

    @Override
    public V put(K var1, V var2) {
        // 加写锁
        writeLock.lock();
        V v = super.put(var1,var2);
        writeLock.unlock();
        return v;
    }


    @Override
    public V get(Object var1) {
        // 加读锁
        readLock.lock();
        V v = super.get(var1);
        readLock.unlock();
        return v;
    }


}
