package com.hs.wl.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * LRU缓存
 */
public class LruCache extends LinkedHashMap<String, List<String>> {
	private final int capacity;

	public LruCache(int capacity) {
		super(capacity, 0.75F, true);
		this.capacity = capacity;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry eldest) {
		return size() > capacity;
	}

}
