package com.example.demo;

import com.example.demo.controller.ShortUrl;
import com.example.demo.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShorturlApplicationTests {

    @Autowired
    private ShortUrl shortUrl;
    @Test
    void test1() {
        shortUrl.getShortUrl("http://baidu.com");


    }

    @Test
    void test2() {
        shortUrl.getLongUrl("http://baidu.com");
    }
}
