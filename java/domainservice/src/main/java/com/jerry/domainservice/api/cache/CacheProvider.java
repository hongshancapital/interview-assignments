package com.jerry.domainservice.api.cache;

import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoMatchedObjectException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;

/**
 * Cache provider
 * @author jerry
 *
 * @param <K>
 * @param <V>
 */
public interface CacheProvider<K,V> {
	void add(K key,V obj) throws CachedObjectAlreadyExistedException, NoSpaceException;
	void evict();
	V get(K key) throws NoMatchedObjectException;
	boolean containsKey(K key);
}
