package com.shorts.url.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ShortUrlServiceTests {

    @Resource
    private ShortUrlService shortUrlService;

    @Test
    void testGetShortUrl() {
        String longUrl = "http://www.baidu.com";
        String shortUrl = shortUrlService.getShortUrl(longUrl);
        Assertions.assertNotNull(shortUrl);
    }

    @Test
    void testGetLongUrl() {
        String longUrl = "http://www.baidu.com";
        String shortUrl = shortUrlService.getShortUrl(longUrl);
        String orgUrl = shortUrlService.getLongUrl(shortUrl);
        Assertions.assertEquals(longUrl, orgUrl);
    }

}
