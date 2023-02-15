package com.yilong.shorturl.service;

import com.yilong.shorturl.common.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Test
    public void testSaveOriginUrl() {
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        String shortUrl = urlService.saveOriginUrl(longUrl);
        Assertions.assertEquals("https://8.cn/1Evr03", shortUrl);
    }

    @Test
    public void testStorageFull() {
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        String shortUrl = urlService.saveOriginUrl(longUrl);
        Assertions.assertEquals("https://8.cn/1Evr03", shortUrl);
    }

    @Test
    public void testGetOriginUrlByShort() {
        String invalidShortUrl = "https:/8.cn/1Evr03123";
        Assertions.assertThrows(BusinessException.class, () -> urlService.getOriginUrlByShort(invalidShortUrl));

        String notExistShortUrl = "https:/8.cn/notIN";
        Assertions.assertThrows(BusinessException.class, () -> urlService.getOriginUrlByShort(notExistShortUrl));

        String existShortUrl = "https:/8.cn/1Evr03";
        String longUrl = "https://www.vampire.com/url/looooooong/1";
        urlService.saveOriginUrl(longUrl);
        Assertions.assertEquals(longUrl, urlService.getOriginUrlByShort(existShortUrl));
    }
}
