package com.sequoia.shorturl.web.service.impl;

import com.sequoia.shorturl.web.service.IUrlConvertorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UrlConvertorServiceImplTest {

    @Autowired
    private IUrlConvertorService urlConvertorService;

    private static final String testUrl = "https://www.sina.com.cn";

    @Test
    public void getLongUrlByShortUrl() {
        // 保存短链接
        String shortUrl = urlConvertorService.longUrlToShortUrl(testUrl);
        String longUrl = urlConvertorService.getLongUrlByShortUrl(shortUrl);
        assertEquals(testUrl, longUrl);
    }

    @Test
    public void longUrlToShortUrl() {
        // 第一次保存短链接
        String shortUrl = urlConvertorService.longUrlToShortUrl(testUrl);

        // 再次保存相同的长链接
        String shortUrl2 = urlConvertorService.longUrlToShortUrl(testUrl);
        // 两次短链接应该相同
        assertEquals(shortUrl, shortUrl2);
    }

}