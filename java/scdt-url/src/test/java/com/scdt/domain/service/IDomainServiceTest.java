package com.scdt.domain.service;

import com.scdt.domain.Constant;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class IDomainServiceTest {

    @Autowired
    IDomainService domainService;

    @Test
    void getLongUrlByShortUrl() {
        String shortUrl = domainService.getShortUrlByLongUrl(Constant.DEMO_LONG_URL);
        String longUrl = domainService.getLongUrlByShortUrl(shortUrl);
        Assert.assertNotNull(longUrl);
    }

    @Test
    void getShortUrlByLongUrl() {
        String shortUrl = domainService.getShortUrlByLongUrl(Constant.DEMO_LONG_URL);
        Assert.assertNotNull(shortUrl);
    }
}