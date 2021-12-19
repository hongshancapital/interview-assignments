package com.shycier.shorturl.service;

/**
 * 键值对存储
 */
public interface Store<K,V> {

	/**
	 * 保存键值对
	 * @param key    键
	 * @param value  值
	 */
	void save(K key, V value);

	/**
	 * 通过键获取值
	 * @param key 键
	 * @return    值
	 */
	V get(K key);

}
