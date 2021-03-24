package com.wuhui.shorturl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ShortUrlServiceTest {

    @Resource
    private ShortUrlService shortUrlService;
    private String sourceUrl = "https://qq.com";
    private String shortUrl = null;

    @BeforeEach
    void createShortUrl() {
        String shortUrl = shortUrlService.createShortUrl(sourceUrl);
        assertNotNull(shortUrl);
        this.shortUrl = shortUrl;
    }

    @Test
    void findSourceUrl() {
        String sourceUrl = shortUrlService.findSourceUrl(this.shortUrl);
        assertNotNull(sourceUrl);
        assertEquals(this.sourceUrl, sourceUrl);
    }

    @Test
    void createShortUrl2() {
        String sourceUrl = "https://baidu.com/" + Math.random();
        String shortUrl = shortUrlService.createShortUrl(sourceUrl);
        assertNotNull(shortUrl);
    }

    @Test
    void findSourceUrl2() {
        try {
            String sourceUrl = shortUrlService.findSourceUrl(String.valueOf(Math.random()));
            assertNotNull(sourceUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}