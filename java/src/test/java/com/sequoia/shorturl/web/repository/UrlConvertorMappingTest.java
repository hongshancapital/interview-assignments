package com.sequoia.shorturl.web.repository;

import com.sequoia.shorturl.common.server.ShortUrlGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class UrlConvertorMappingTest {

    @Autowired
    private UrlConvertorMapping urlConvertorMapping;

    private static final String testUrl = "https://www.sina.com.cn";

    @Test
    public void getLongUrlByShortUrl() {
        String shortUrl = ShortUrlGenerator.generate(testUrl);
        urlConvertorMapping.put(shortUrl, testUrl);
        String longUrl = urlConvertorMapping.get(shortUrl);
        assertEquals(testUrl, longUrl);
    }

    @Test
    public void size() {
        String originUrl = urlConvertorMapping.put("1pvSQD", testUrl);
        assertEquals(1, urlConvertorMapping.size());
    }


    @Test
    public void cleanTask() throws InterruptedException {
        UrlConvertorMapping urlConvertorMapping = new UrlConvertorMapping(2000, 1, TimeUnit.SECONDS);
        String put = urlConvertorMapping.put("1pvSQD", testUrl);
        assertEquals(testUrl,urlConvertorMapping.get("1pvSQD"));
        Thread.sleep(3000L);

        assertEquals("",urlConvertorMapping.get("1pvSQD"));
    }


}