package com.scdt.china.shorturl;

import com.scdt.china.shorturl.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test-application.properties")
@ContextConfiguration(classes=TestConfig.class)
public class ShortUrlCacheEvictTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void testCacheEvict() throws InterruptedException {

        String shortUrl = shortUrlService.generateShortUrl("www.google.com");

        for (int i = 0 ; i < 100; i++) {
            shortUrlService.generateShortUrl("www.baidu.com" + i);
        }

        //wait cache aysn evict
        Thread.sleep(1000L);

        String otherShortUrl = shortUrlService.generateShortUrl("www.google.com");

        Assert.assertTrue(!otherShortUrl.equals(shortUrl));
    }

}
