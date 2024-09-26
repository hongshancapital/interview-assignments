package com.example.shorturl.manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author yingchen
 * @date 2022/3/15
 */
@SpringBootTest
class ShortUrlManagerTest {
    @Autowired
    ShortUrlManager shortUrlManager;

    @Test
    void getShortUrl() {
        String originalUrl = "https://www.baidu.com";
        final String shortUrl = shortUrlManager.getShortUrl(originalUrl);
        Assert.isTrue(shortUrl != null);
    }

    @Test
    void getOriginalUrl() {
        String originalUrl = "https://www.baidu.com";
        final String shortUrl = shortUrlManager.getShortUrl(originalUrl);
        final String originalUrl1 = shortUrlManager.getOriginalUrl(shortUrl);
        Assert.isTrue(Objects.equals(originalUrl, originalUrl1), "originalUrl not match");
        final String shortUrl1 = shortUrlManager.getShortUrl(originalUrl);
        Assert.isTrue(Objects.equals(shortUrl, shortUrl1), "shortUrl not match");
    }

    @Test
    void testCache() {
        String originalUrl = "https://www.baidu.com";
        final String shortUrl = shortUrlManager.getShortUrl(originalUrl);
        final String originalUrl1 = shortUrlManager.getOriginalUrl(shortUrl);
        Assert.isTrue(Objects.equals(originalUrl, originalUrl1), "originalUrl not match");
        final String shortUrl1 = shortUrlManager.getShortUrl(originalUrl);
        Assert.isTrue(Objects.equals(shortUrl, shortUrl1), "shortUrl not match");
    }
}