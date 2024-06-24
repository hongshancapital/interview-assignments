package com.eagle.urlTransformer.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlCacheTest {
	
	@Test
	void testPutUrlPairNullParam() {
		
		String shortUrl1 = UrlCache.putUrlPair(null, "http://random");
		assertNull(shortUrl1);
		
		String shortUrl2 = UrlCache.putUrlPair(new String[] {}, "http://random");
		assertNull(shortUrl2);
		
		String shortUrl3 = UrlCache.putUrlPair(new String[] {"xxx"}, null);
		assertNull(shortUrl3);
		
		String shortUrl4 = UrlCache.putUrlPair(new String[] {"","xxx"}, "http://random");
		assertNotNull(shortUrl4);
	}

	@Test
	void testIsLongUrlExistsNullParam() {
		String shortUrl = UrlCache.isLongUrlExists(null);
		assertNull(shortUrl);
	}

	@Test
	void testGetLongUrlByShort() {
		String longUrl1 = UrlCache.getLongUrlByShort(null);
		assertNull(longUrl1);
	}

}
