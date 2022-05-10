package com.hongshan;

import com.hongshan.url.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppApplicationTest {

    @Autowired
    private ShortUrlService shortUrlService;


    @Test
    public void testGetShortUrl() {
        String shortUrl = shortUrlService.getShortUrl("http://www.baidu.com", 6);
        System.out.println(shortUrl);
    }

    @Test
    public void testGetCommonUrl() {
        String shortUrl = shortUrlService.getShortUrl("http://www.baidu.com", 6);
        String commonUrl = shortUrlService.getCommonUrl(shortUrl);
        System.out.println(commonUrl);
    }

}