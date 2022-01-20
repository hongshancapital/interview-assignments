package com.eagle.urlTransformer.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@SpringBootTest
class UrlCaffeineCacheTest {

	@Test
	void testPutUrlPair() {
		String result1 = UrlCaffeineCache.putUrlPair(new String[]{"eagle1","eagle2","eagle3"},"19");
		assertNotNull(result1);
		String result2 = UrlCaffeineCache.putUrlPair(new String[]{"eagle1","eagle2","eagle3"},"29");
		assertNotNull(result2);
		String result3 = UrlCaffeineCache.putUrlPair(new String[]{"eagle1","eagle2","eagle3"},"39");
		assertNotNull(result3);
		String result4 = UrlCaffeineCache.putUrlPair(new String[]{"eagle1","eagle2","eagle3"},"14");
		assertNull(result4);
		String result5 = UrlCaffeineCache.putUrlPair(new String[]{"eagle1","eagle2","eagle3","eagle4"},"24");
		assertNotNull(result5);
	}

	@Test
	void testIsLongUrlExists() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLongUrlByShort() {
		Cache<String, String> short2LongMap = Caffeine.newBuilder()
			    // 设置最后一次写入经过600s过期
			    .expireAfterAccess(600, TimeUnit.SECONDS)
			    // 初始的缓存空间大小
			    .initialCapacity(Constants.URL_CACHE_SIZE/100)
			    // 缓存的最大条数
			    .maximumSize(Constants.URL_CACHE_SIZE)
			    .build();;
	    System.out.println(short2LongMap);
	}

	@Test
	void testPutLongUrlString() {
		fail("Not yet implemented");
	}

	@Test
	void testPutLongUrlStringString() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveLongUrl() {
		fail("Not yet implemented");
	}

	@Test
	void testGetShortUrlByLongUrl() {
		fail("Not yet implemented");
	}

}
