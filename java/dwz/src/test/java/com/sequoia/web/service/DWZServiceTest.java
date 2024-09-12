package com.sequoia.web.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DWZServiceTest {
    @Autowired
    private DWZService service;

    @Test
    public void getLongUrlByShortUrl(){
        String originalUrl = "www.baidu.com";
        String shortUrl = service.saveShortUrlByLongUrl(originalUrl);
        String longUrl = service.getLongUrlByShortUrl(shortUrl);
        Assert.assertEquals(originalUrl, longUrl);

        // 第二次保存，值相同
        String shortUrl2 = service.saveShortUrlByLongUrl(originalUrl);
        Assert.assertEquals(shortUrl, shortUrl2);
    }
}
