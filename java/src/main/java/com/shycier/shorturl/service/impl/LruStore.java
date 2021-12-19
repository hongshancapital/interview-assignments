package com.shycier.shorturl.service.impl;

import com.shycier.shorturl.service.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 加入了LRU缓存淘汰策略且线程安全的键值对存储
 */
@Component
public class LruStore<K,V> implements Store<K,V> {

	/**
	 * 容器初始化大小
	 * 默认100
	 */
	@Value("${shortUrl.store.initSize}")
	private int initSize = 100;

	/**
	 * 最大键值对数量
	 * 当键值对数量超过该值时触发缓存淘汰
	 * 默认1000万 当<=0时 不根据键值对数量进行缓存淘汰
	 */
	@Value("${shortUrl.store.maxSize}")
	private long maxSize = 10 * 1000 * 1000;

	/**
	 * 最大内存
	 * 当JVM内存超过该值时触发缓存淘汰
	 * 默认1GB 当<=0时 不根据内存进行缓存淘汰
	 */
	@Value("${shortUrl.store.maxMemory}")
	private long maxMemory = 1024 * 1024 * 1024;

	/**
	 * 存储键值对的Map
	 * 这里创建了一个线程安全且具备LRU缓存淘汰机制的Map
	 */
	private final Map<K, V> store = Collections.synchronizedMap(
			new LinkedHashMap<K, V>(initSize, 75, true) {
				@Override
				protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
					long totalMemory = Runtime.getRuntime().totalMemory();
					long freeMemory  = Runtime.getRuntime().freeMemory();
					long usedMemory  = totalMemory - freeMemory;
					return (maxSize > 0 && size() > maxSize) || (
							LruStore.this.maxMemory > 0 && usedMemory > LruStore.this.maxMemory);
				}
			});

	/**
	 * 保存键值对
	 * @param key    键
	 * @param value  值
	 */
	@Override
	public void save(K key, V value) {
		store.putIfAbsent(key, value);
	}

	/**
	 * 获取键值对数量
	 * @return 键值对数量
	 */
	public int size() {
		return store.size();
	}

	/**
	 * 设置触发缓存淘汰的数量阈值
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * 设置触发缓存淘汰的内存阈值
	 */
	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	/**
	 * 清除缓存数据
	 */
	public void clear() {
		this.store.clear();
	}

	/**
	 * 通过键获取值
	 * @param key 键
	 * @return    值
	 */
	@Override
	public V get(K key) {
		System.out.println(store.size());
		return store.get(key);
	}

}
