package com.jerry.domainservice.api.cache.impl;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.jerry.domainservice.api.cache.CacheProvider;
import com.jerry.domainservice.api.cache.CachedEntityWrapper;
import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoMatchedObjectException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * A simple cache provider implemented by CurrentHashMap
 * 
 * @author jerry
 *
 * @param <K>
 * @param <V>
 */
public final @Slf4j class HashMapCacheProvider<K, V> implements CacheProvider<K, V> {

	private ConcurrentHashMap<K, CachedEntityWrapper<V>> cacheMapping = new ConcurrentHashMap<>();

	private CopyOnWriteArrayList<K> idList = new CopyOnWriteArrayList<>();

	private AtomicInteger total = new AtomicInteger(0);

	@Setter
	private long maxCacheSize = 0;

	@Setter
	// 每次准备抛弃的缓存数量， 默认20
	private int numberShouldBeEvicted = 20;

	@Setter
	// 缓存存活期间，如果当前时间 - 缓存最后访问时间 > survivePeriod， 那么该缓存则可以被清理。 默认60秒
	private long survivePeriod = 60000;// 180000L;

	@Override
	public void add(K key, V obj) throws CachedObjectAlreadyExistedException, NoSpaceException {

		if (cacheMapping.size() > maxCacheSize) {
			throw new NoSpaceException("Service Rejected.");
		}

		if (cacheMapping.containsKey(key)) {
			throw new CachedObjectAlreadyExistedException("Key " + key + "is existed in the cache.");
		}

		if (cacheMapping.contains(new CachedEntityWrapper<>(obj,System.currentTimeMillis()))) {
			throw new CachedObjectAlreadyExistedException("Cache object " + obj + " is existed in the cache.");
		}

		// put id
		idList.add(key);
		CachedEntityWrapper<V> wrapper = new CachedEntityWrapper<>(obj, System.currentTimeMillis());
		cacheMapping.put(key, wrapper);
		total.incrementAndGet();

	}

	@Override
	public void evict() {
		
		log.trace("Try to evict.");
		
		// 如果缓存数量小于最大缓存数的60%， 则跳过
		if (cacheMapping.size() < maxCacheSize * 0.6) {
			return;
		}

		log.trace("Try to evict the last " + numberShouldBeEvicted + " number of domains.");

		// 取指定个数随机短域名信息
		for (int i = 0; i < numberShouldBeEvicted; i++) {
			Random random = new Random();
			int randomNumber = total.get() == 0 ? 0 : random.ints(0, total.get()).findFirst().getAsInt();
			if (randomNumber < idList.size()) {
				K id = idList.get(randomNumber);
				CachedEntityWrapper<V> wrapper = cacheMapping.get(id);
				// 如果缓存对象测试不通过
				if (wrapper != null && System.currentTimeMillis() - wrapper.getLastUpdateTime() > survivePeriod) {
					idList.remove(id);
					cacheMapping.remove(id);
					total.decrementAndGet();
					log.trace("id={} will be evicted.", id);
				}
			}
		}

		log.trace("Domain cache has been evicted.");

	}

	@Override
	public V get(K key) throws NoMatchedObjectException {
		if (!cacheMapping.containsKey(key)) {
			throw new NoMatchedObjectException("There is no object matched the key " + key + ".");
		}
		CachedEntityWrapper<V> wrapper = cacheMapping.get(key);
		wrapper.setLastUpdateTime(System.currentTimeMillis());
		return wrapper.getEntity();
	}
	
	public boolean containsKey(K key) {
		return cacheMapping.containsKey(key);
	}
}
