package com.scdt.china.shorturl;

import com.scdt.china.shorturl.service.ShortUrlGenerator;
import com.scdt.china.shorturl.service.ShortUrlService;
import com.scdt.china.shorturl.service.exception.BizException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class ShortUrlGenerateConflictTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @SpyBean
    private ShortUrlGenerator shortUrlGenerator;

    @Test
    public void testGenerateConflicted() {

        when(shortUrlGenerator.generateShortUrl(Mockito.anyString())).thenReturn("same");

        shortUrlService.generateShortUrl("www.google.com");
        String shortUrl = shortUrlService.generateShortUrl("www.baidu.com");

        Assert.assertTrue(!shortUrl.equals("same"));
    }

    @Test
    public void testGenerateTryOverCount() {

        when(shortUrlGenerator.generateShortUrl(Mockito.anyString())).thenReturn("same1");
        when(shortUrlGenerator.generateShortUrlWhenConflicted(Mockito.anyString(),Mockito.anyString())).thenReturn("same1");

        shortUrlService.generateShortUrl("www.google1.com");
        Exception exception = assertThrows(BizException.class, () -> {
            shortUrlService.generateShortUrl("www.baidu1.com.cn");
        });
        Assert.assertTrue(exception.getMessage().contains("try again"));
    }

}
