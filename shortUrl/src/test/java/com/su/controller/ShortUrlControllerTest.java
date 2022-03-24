package com.su.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ShortUrlControllerTest {

    @Autowired
    private ShortUrlController shortUrlController;

    @Test
    public void getShortUrl(){
        shortUrlController.getShortUrl("http://www.baidu.com");
    }

    @Test
    public void getLongUrl(){
        String longUrl = "http://www.baidu.com";
        String url = shortUrlController.getShortUrl(longUrl);
        Assert.isTrue(longUrl.equals(shortUrlController.getLongUrl(url)));
    }
}
