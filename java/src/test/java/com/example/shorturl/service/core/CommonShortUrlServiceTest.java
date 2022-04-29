package com.example.shorturl.service.core;

import com.example.shorturl.ShorturlApplication;
import com.example.shorturl.config.exception.BizException;
import com.example.shorturl.service.dto.ShortUrlDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class CommonShortUrlServiceTest {
    @Resource(name = "randomShotUrlService")
    CommonShortUrlService shortUrlService;

    @Test(expected = BizException.class)
    public void testCommonShortUrlService() {
        String longUrl = "http://www.baidu.com";
        ShortUrlDto shortUrlDto = shortUrlService.getShortUrl(longUrl);
        Assert.assertNotNull(shortUrlDto);
        Assert.assertEquals(longUrl, shortUrlService.getLongUrl(shortUrlDto.getShortUrl()));

        String shortUrl = shortUrlDto.getShortUrl();
        shortUrlService.getLongUrl(shortUrl);
    }

    @Test(expected = BizException.class)
    public void getLongUrl() {
        String shortUrl = "http://www.baidu.com";
        shortUrlService.getLongUrl(shortUrl);
    }

    @Test(expected = BizException.class)
    public void getShortUrlException() {
        shortUrlService.getShortUrl("");
    }

    @Test(expected = BizException.class)
    public void getLongUrlException() {
        shortUrlService.getLongUrl("");
    }
}