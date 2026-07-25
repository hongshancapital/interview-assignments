package com.scdt.china.shorturl;

import com.scdt.china.shorturl.service.ShortUrlService;
import com.scdt.china.shorturl.service.exception.BizException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;

    private static final String TEST_URL = "www.baidu.com.cn";

    @Test
    public void testGenerate() {

        Exception exception = assertThrows(BizException.class, () -> {
            shortUrlService.generateShortUrl("");
        });

        Assert.assertTrue(exception.getMessage().contains("can not be empty"));

        String shortUrl = shortUrlService.generateShortUrl(TEST_URL);
        Assert.assertTrue(shortUrl.length() <= 8);
    }

    @Test
    public void testFetchUrl() {

        Exception exception = assertThrows(BizException.class, () -> {
            shortUrlService.fetchUrl("");
        });

        Assert.assertTrue(exception.getMessage().contains("can not be empty"));

        String shortUrl = shortUrlService.generateShortUrl(TEST_URL);
        String url = shortUrlService.fetchUrl(shortUrl);
        Assert.assertTrue(url.equals(TEST_URL));
    }

    @Test
    public void testGenerateWithSameUrl() {
        String shortUrl = shortUrlService.generateShortUrl(TEST_URL);
        String otherShortUrl = shortUrlService.generateShortUrl(TEST_URL);
        Assert.assertTrue(shortUrl.equals(otherShortUrl));
    }

    @Test
    public void testGenerateWithInvalidUrl() {

        Exception exception = assertThrows(BizException.class, () -> {
            shortUrlService.generateShortUrl("123456");
        });

        Assert.assertTrue(exception.getMessage().contains("incorrect"));
    }

}
