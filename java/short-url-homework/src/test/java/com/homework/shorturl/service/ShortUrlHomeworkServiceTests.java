package com.homework.shorturl.service;

import com.homework.shorturl.exception.ShortUrlBusinessException;
import com.homework.shorturl.res.FullUrlRes;
import com.homework.shorturl.res.ShortUrlRes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShortUrlHomeworkServiceTests {

    @Autowired
    private ShortUrlService shortUrlService;

    @AfterEach
    public void after() {
        shortUrlService.clearAll();
    }

    @Test
    void generateShortUrlByFullUrl() {
        ShortUrlRes shortUrlRes = shortUrlService.generateShortUrlByFullUrl("https://github.com/xjyl2845613/short-url-homework");
        assertEquals(shortUrlRes.getUrl(), "xxy.cn/y");
    }

    @Test
    void generateShortUrlByFullUrlExist() {
        shortUrlService.generateShortUrlByFullUrl("https://github.com/xjyl2845613/short-url-homework");
        ShortUrlRes shortUrlResSec = shortUrlService.generateShortUrlByFullUrl("https://github.com/xjyl2845613/short-url-homework");
        assertEquals(shortUrlResSec.getUrl(), "xxy.cn/y");
    }

    @Test
    void generateShortUrlByFullUrlNotValidUrl() {
        ShortUrlBusinessException shortUrlBusinessException = Assertions.assertThrows(ShortUrlBusinessException.class, () ->
                shortUrlService.generateShortUrlByFullUrl("github.com/xjyl2845613/short-url-homework"));

        assertEquals("Input url not valid", shortUrlBusinessException.getMessage());
    }

    @Test
    void generateShortUrlByFullUrlIdExceed() throws Exception {
        ReflectionTestUtils.setField(shortUrlService, "idFlag", 218340105584896L);
        ShortUrlBusinessException shortUrlBusinessException = Assertions.assertThrows(ShortUrlBusinessException.class, () ->
                shortUrlService.generateShortUrlByFullUrl("https://github.com/xjyl2845613/short-url-homework"));
        assertEquals("Out of max number of short url number", shortUrlBusinessException.getMessage());
    }

    @Test
    void getFullUrlByShortUrl() {
        ShortUrlRes shortUrlRes = shortUrlService.generateShortUrlByFullUrl("https://github.com/xjyl2845613/short-url-homework");
        FullUrlRes fullUrlByShortUrl = shortUrlService.getFullUrlByShortUrl(shortUrlRes.getUrl());

        assertEquals(fullUrlByShortUrl.getUrl(), "https://github.com/xjyl2845613/short-url-homework");
    }

    @Test
    void getFullUrlByShortUrlNotExist() {
        shortUrlService.generateShortUrlByFullUrl("https://github.com/xjyl2845613/short-url-homework");

        ShortUrlBusinessException shortUrlBusinessException = Assertions.assertThrows(ShortUrlBusinessException.class, () ->
                shortUrlService.getFullUrlByShortUrl("xxy.cn/yz"));

        assertEquals("Short url: xxy.cn/yz not found full url", shortUrlBusinessException.getMessage());
    }

    @Test
    void getFullUrlByShortUrlNotStartWithShortUrlPrefix() {
        ShortUrlBusinessException shortUrlBusinessException = Assertions.assertThrows(ShortUrlBusinessException.class, () ->
                shortUrlService.getFullUrlByShortUrl("1xxy.cn/yz"));

        assertEquals("Short url: 1xxy.cn/yz not valid", shortUrlBusinessException.getMessage());
    }
}
