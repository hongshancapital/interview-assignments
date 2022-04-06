package com.domain.urlshortener.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 2:08
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UrlShortenerServiceTest {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Test
    public void test_createShortUrl() {
        String longUrl = "http://xx.com";
        String shortUrl = urlShortenerService.createShortUrl(longUrl);
    }

    @Test
    public void test_getLongUrl() {
        test_createShortUrl();
        String shortUrl = "https://m.dev.com/G8";
        String longUrl = urlShortenerService.getLongUrl(shortUrl);
    }

}
