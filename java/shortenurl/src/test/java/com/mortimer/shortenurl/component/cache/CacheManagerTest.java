package com.mortimer.shortenurl.component.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheManagerTest {
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void test_getShortUrl() {
        String longUrl = "http://www.testurl.com";
        String shortUrl = cacheManager.getShortUrl(longUrl);
        Assertions.assertNotNull(shortUrl);

        StringBuilder sb = new StringBuilder("https://");
        for (int i = 0; i < 100; i++) {
            sb.append("thisisaverylongurl");
        }
        sb.append(".com");
        shortUrl = cacheManager.getShortUrl(sb.toString());
        Assertions.assertNotNull(shortUrl);

        shortUrl = cacheManager.getShortUrl(null);
        Assertions.assertNull(shortUrl);
    }

    @Test
    public void test_getLongUrl() {
        String longUrl = "http://www.testurl.com";
        String shortUrl = cacheManager.getShortUrl(longUrl);
        Assertions.assertEquals(longUrl, cacheManager.getLongUrl(shortUrl));
    }
}
