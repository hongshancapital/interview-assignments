package com.jerry.domainservice.api.cache.impl;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoMatchedObjectException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;
@ExtendWith(MockitoExtension.class)
class HashMapCacheProviderTest {
	
	@InjectMocks
	HashMapCacheProvider<String,String> cacheProvider;

	final static long MAX_CACHE_SIZE = 100; 
	
	final static int NUMBER_SHOULD_BE_EVICTED = 20;
	
	final static long SURVIVE_PERIOD = 5000;
	
	@BeforeEach
	void setup() {
		cacheProvider.setMaxCacheSize(MAX_CACHE_SIZE);
		cacheProvider.setNumberShouldBeEvicted(NUMBER_SHOULD_BE_EVICTED);
		cacheProvider.setSurvivePeriod(SURVIVE_PERIOD);
	}
	
	@Test
	void testAdd() {
		Assertions.assertDoesNotThrow(()->cacheProvider.add("1", "testvalue"));
	}
	
	@Test
	void testAddWithExceptions() {
		// 测试重复KEY
		Assertions.assertThrows(CachedObjectAlreadyExistedException.class,()->{
			cacheProvider.add("1", "testvalue");
			cacheProvider.add("1", "testvalue1");
		});
		
		// 测试重复的对象
		Assertions.assertThrows(CachedObjectAlreadyExistedException.class,()->{
			cacheProvider.add("2", "testvalue2");
			cacheProvider.add("3", "testvalue2");
		});
	}

	@Test
	void testEvict() throws CachedObjectAlreadyExistedException, NoSpaceException {
		for(int i=0;i<MAX_CACHE_SIZE;i++) {
			cacheProvider.add(String.valueOf(i), "testvalue" + String.valueOf(i));	
		}
		cacheProvider.evict();
	}
	
	@Test
	// 测试
	void testEvict2() throws CachedObjectAlreadyExistedException, NoSpaceException {
		for(int i=0;i<MAX_CACHE_SIZE*.5;i++) {
			cacheProvider.add(String.valueOf(i), "testvalue" + String.valueOf(i));	
		}
		cacheProvider.evict();
	}
	
	@Test
	void testEvictWithException() throws CachedObjectAlreadyExistedException, NoSpaceException, InterruptedException {
		cacheProvider.setMaxCacheSize(10);
		for(int i=0;i<10 ;i++) {
			cacheProvider.add(String.valueOf(i), "testvalue" + String.valueOf(i));	
		}
		Thread.sleep(SURVIVE_PERIOD);
		cacheProvider.evict();
		Assertions.assertThrows(NoMatchedObjectException.class,()->cacheProvider.get("1"));
	}

	@Test
	void testGet() throws NoMatchedObjectException, CachedObjectAlreadyExistedException, NoSpaceException {
		cacheProvider.add("1", "testvalue1");
		Assertions.assertEquals(cacheProvider.get("1"),"testvalue1");
	}
	
	@Test
	void testGetWithExceptions() throws NoMatchedObjectException, CachedObjectAlreadyExistedException, NoSpaceException {
		cacheProvider.add("1", "testvalue1");
		Assertions.assertThrows(NoMatchedObjectException.class,()->cacheProvider.get("2"),"testvalue1");
	}

}
