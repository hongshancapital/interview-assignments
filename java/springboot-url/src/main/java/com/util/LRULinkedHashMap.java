package com.util;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单实现LRU算法且线程安全的linkedhashmap
 *
 * @param <K>
 * @param <V>
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	private final int maxCapacity;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private final Lock lock = new ReentrantLock();

	/**
	 * 初始化大小
	 * @param maxCapacity
	 */
	public LRULinkedHashMap(int maxCapacity) {
		super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
		this.maxCapacity = maxCapacity;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > maxCapacity;
	}

	@Override
	public V get(Object key) {
		try {
			lock.lock();
			return super.get(key);
		} finally {
			lock.unlock();
		}
	}

	// 对不同的操作加锁
	@Override
	public V put(K key, V value) {
		try {
			lock.lock();
			return super.put(key, value);
		} finally {
			lock.unlock();
		}
	}
}
