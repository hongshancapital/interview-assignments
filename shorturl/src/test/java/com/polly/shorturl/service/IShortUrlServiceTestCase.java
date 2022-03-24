package com.polly.shorturl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author polly
 * @date 2022.03.24 12:15:46
 */
@SpringBootTest
public class IShortUrlServiceTestCase {

    @Autowired
    private IShortUrlService service;

    @Test
    public void insertAndGet() {
        String shortUrl = service.insertShortUrl("test");
        System.out.println(shortUrl);
        String longUrl = service.getUrlByShortUrl("shortUrl");
        System.out.println(longUrl);

    }

    @Test
    public void get() {
        String longUrl = service.getUrlByShortUrl("shj");
        System.out.println(longUrl);
    }
}
