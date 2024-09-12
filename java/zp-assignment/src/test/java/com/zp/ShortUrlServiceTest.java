package com.zp;

import org.junit.Test;
import com.zp.service.ShortUrlService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlServiceTest {

    @Autowired
    ShortUrlService shortUrlService;

    @Value("${base.url}")
    private String baseUrl;

    @Test
    public void getShortUrl() {
        String shortUrl = shortUrlService.getShortUrl("www.baidu.com");
        assertEquals(shortUrl, baseUrl + "btmshx");
    }

    @Test
    public void getLongUrl() {
        String shortUrl = shortUrlService.getShortUrl("www.baidu.com");
        String longUrl = shortUrlService.getLongUrl(shortUrl);
        assertEquals(longUrl, "www.baidu.com");
    }
}
