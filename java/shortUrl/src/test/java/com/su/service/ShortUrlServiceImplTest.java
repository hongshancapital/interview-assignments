package com.su.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ShortUrlServiceImplTest {

    @Autowired
    private IShortUrlService shortUrlService;

    @Test
    public void getShortUrl(){
        shortUrlService.getShortUrl("http://www.baidu.com");
    }

    @Test
    public void getLongUrl(){
        String longUrl = "http://www.baidu.com";
        String url = shortUrlService.getShortUrl(longUrl);
        Assert.isTrue(longUrl.equals(shortUrlService.getLongUrl(url)));
    }

    @Test
    public void getInvalidUrl(){
        String invalid = "sss";
        Exception exception = assertThrows(RuntimeException.class, () -> {
            shortUrlService.getLongUrl(invalid);
        });
        assertTrue(exception.getMessage().contains(invalid+" 非法"));
    }
}
