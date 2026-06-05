package com.scdt.china.shorturl.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.scdt.china.shorturl.util.ShortUrlGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class ShortUrlServiceImplTests {

    @Autowired
    private ShortUrlServiceImpl shortUrlService;

    String url = "https://test.com/test";

    String wrongShort = "123456";


    @Test
    public void testGetShortUrl() {
        String shortUrl = shortUrlService.getShortUrl(url);
        Assert.notBlank(shortUrl);
        Assert.isTrue(shortUrl.length() == 6);
    }

    @Test
    public void testGetShortUrlNull() {
        MockedStatic<ShortUrlGenerator> theMock = Mockito.mockStatic(ShortUrlGenerator.class);
        theMock.when(() -> ShortUrlGenerator.shortUrl(url)).thenReturn(wrongShort);
        theMock.when(() -> ShortUrlGenerator.shortUrl(url + "123")).thenReturn(wrongShort);
        theMock.when(() -> ShortUrlGenerator.shortUrl(SecureUtil.md5(url + "123"))).thenReturn(wrongShort);

        String shortUrl = shortUrlService.getShortUrl(url);
        Assert.isTrue(wrongShort.equals(shortUrl));
        String secondShortUrl = shortUrlService.getShortUrl(url + "123");
        Assert.isNull(secondShortUrl);
        theMock.close();
    }

    @Test
    public void testGetShortUrlSecondRight() {
        MockedStatic<ShortUrlGenerator> theMock = Mockito.mockStatic(ShortUrlGenerator.class);
        theMock.when(() -> ShortUrlGenerator.shortUrl(url)).thenReturn(wrongShort);
        theMock.when(() -> ShortUrlGenerator.shortUrl(url + "1234")).thenReturn(wrongShort);
        theMock.when(() -> ShortUrlGenerator.shortUrl(SecureUtil.md5(url + "1234"))).thenReturn("abides");

        String shortUrl = shortUrlService.getShortUrl(url);
        Assert.isTrue(wrongShort.equals(shortUrl));
        String secondShortUrl = shortUrlService.getShortUrl(url + "1234");
        Assert.notBlank(secondShortUrl);
        theMock.close();
    }

    @Test
    public void testGetUrl() {
        String shortUrl = "abc";
        String url = shortUrlService.getUrl(shortUrl);
        Assert.isNull(url);
    }

    @Test
    public void testSetAndGet() {
        String shortUrl = shortUrlService.getShortUrl(url);
        Assert.notBlank(shortUrl);
        String cacheUrl = shortUrlService.getUrl(shortUrl);
        Assert.notBlank(cacheUrl);
        Assert.isTrue(url.equals(cacheUrl));
    }
}
