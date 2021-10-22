package com.jerry.domainservice.api.cache.properties;

import lombok.Data;

@Data
public class HashMapCacheProperties {
	private int numberShouldBeEvicted;
	private long survivePeriod;
	private long maxCacheSize;
}
