package com.jerry.domainservice.api.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jerry.domainservice.api.cache.CacheProvider;
import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoMatchedObjectException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;
import com.jerry.domainservice.api.cache.impl.HashMapCacheProvider;
import com.jerry.domainservice.api.exception.ServiceRejectException;
import com.jerry.domainservice.api.service.DomainInformationBO;

@ExtendWith(MockitoExtension.class)
class IDGenerateServiceBaseTest {

	@InjectMocks
	IDGenerateServiceBase service;

	@Mock
	CacheProvider<String, DomainInformationBO> cacheProvider;

	@Test
	void testGenerate() {
		Assertions.assertDoesNotThrow(() -> service.generate("www.baidu.com"));
	}

	@Test
	void testGenerateWithDomainExistedException() throws CachedObjectAlreadyExistedException, NoSpaceException {
		doThrow(NoSpaceException.class).when(cacheProvider).add(any(), any());
		Assertions.assertThrows(ServiceRejectException.class, () -> {
			service.generate("www.baidu.com");
		});

		doThrow(CachedObjectAlreadyExistedException.class).when(cacheProvider).add(any(), any());
		Assertions.assertThrows(ServiceRejectException.class, () -> {
			service.generate("www.baidu.com");
		});
	}

	@Test
	void testGetDomainName() throws NoMatchedObjectException {
		DomainInformationBO info = new DomainInformationBOBase();
		info.setDomainName("www.baidu.com");
		doReturn(info).when(cacheProvider).get("a912kxDc");
		Assertions.assertEquals(service.getDomainName("a912kxDc"), "www.baidu.com");
	}

	@Test
	void testGetDomainNameWithNoMatchedDomainException() throws NoMatchedObjectException {
		doThrow(NoMatchedObjectException.class).when(cacheProvider).get("a912kxDc");
		Assertions.assertThrows(ServiceRejectException.class, () -> service.getDomainName("a912kxDc"));
	}

	// 并发测试
	//@Test
	void testWithMultipleThread() throws InterruptedException {
		final int maxThread = 150;
		final int maxLoops = 100000;
		CountDownLatch countDown = new CountDownLatch(maxThread);

		HashMapCacheProvider<String, DomainInformationBO> cacheProvider = new HashMapCacheProvider<>();
		cacheProvider.setMaxCacheSize(8000000);
		cacheProvider.setSurvivePeriod(10000L);
		service.setCacheProvider(cacheProvider);

		for (int i = 0; i < maxThread; i++) {
			final int threadNumber = i;
			Thread thread = new Thread(() -> {
				for (int count = 0; count < maxLoops; count++) {
					StringBuilder domainName = new StringBuilder();
					domainName.append("www.").append(threadNumber).append("baidu").append(count).append("com");
					try {
						String shortName = service.generate(domainName.toString());
						System.out.println(
								"Thread " + threadNumber + " executed " + count + " times, result=" + shortName);
					} catch (ServiceRejectException e) {
						System.out.println("Thread " + threadNumber + " executed " + count + " times, failed by "+e.getMessage());
					}

				}
				countDown.countDown();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});

			thread.start();
			System.out.println("Thread " + i + " started.");
		}
		countDown.await();
	}

}
