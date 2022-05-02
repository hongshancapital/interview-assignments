package com.mortimer.shortenurl.service;

import com.mortimer.shortenurl.service.UrlShortenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UrlShortenServiceTest {
    @Autowired
    private UrlShortenService urlShortenService;

    @Test
    public void test_createShortUrl() {
        String longUrl = "http://www.xyz.com";
        String shortUrl = urlShortenService.createShortUrl(longUrl);
        Assertions.assertNotNull(shortUrl);

        longUrl = "https://www.xyz.cn";
        shortUrl = urlShortenService.createShortUrl(longUrl);
        Assertions.assertNotNull(shortUrl);

        String newShortUrl = urlShortenService.createShortUrl(longUrl);
        Assertions.assertEquals(shortUrl, newShortUrl);
    }

    @Test
    public void test_getLongUrl() {
        String longUrl = "http://xyz.com";
        String shortUrl = urlShortenService.createShortUrl(longUrl);
        String newLongUrl = urlShortenService.getLongUrl(shortUrl.substring(shortUrl.lastIndexOf("/") + 1));
        Assertions.assertEquals(longUrl, newLongUrl);

        Assertions.assertNull(urlShortenService.getLongUrl("abcdefghi"));
    }
}
