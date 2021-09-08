package com.scdt;

import com.scdt.url.service.IShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppTest {
    @Autowired
    IShortUrlService shortUrlService;

    @Test
    public void shortUrlTest() {
        String shortUrl = shortUrlService.shortUrlCreate("http://www.baidu.com");
        log.info("shortUrl:{}", shortUrl);
        String longUrl = shortUrlService.longUrlFind(shortUrl);
        log.info("longUrl:{}", longUrl);
    }
}
