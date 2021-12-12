package org.example;

import org.example.exception.UrlConvertorException;
import org.example.model.UrlCache;
import org.example.model.UrlConvertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
public class UrlConvertorTest {

    @Autowired
    private UrlConvertor urlConvertor;

    @BeforeEach
    public void beforeEach() throws Exception {
        Field shortCodeAndLongUrlMapField = UrlCache.class.getDeclaredField("shortCodeAndLongUrlMap");
        shortCodeAndLongUrlMapField.setAccessible(true);
        shortCodeAndLongUrlMapField.set(null, new ConcurrentHashMap<>());

        Field longUrlAndShortCodeMapField = UrlCache.class.getDeclaredField("longUrlAndShortCodeMap");
        longUrlAndShortCodeMapField.setAccessible(true);
        longUrlAndShortCodeMapField.set(null, new ConcurrentHashMap<>());
    }

    @Test
    @DisplayName("输入一个长域名，返回一个短域名, 输入相同的短域名，返回原长域名")
    public void createShortUrl_test() throws UrlConvertorException {
        String url = "http://www.baidu.com";
        String shortUrl = urlConvertor.createShortUrl(url);
        Assertions.assertTrue(StringUtils.isNotBlank(shortUrl));

        URI uri = URI.create(shortUrl);
        String longUrl = urlConvertor.getLongUrlByShortCode(uri.getPath().replaceAll("/", ""));
        Assertions.assertEquals(url, longUrl);
    }

    @Test
    @DisplayName("连续两次输入同一个长域名，返回同一个短域名")
    public void createShortUrl_test2() throws Exception {
        String url = "http://www.baidu.com";
        String shortUrl = urlConvertor.createShortUrl(url);
        Thread.sleep(2000);
        String shortUrl2 = urlConvertor.createShortUrl(url);
        Assertions.assertEquals(shortUrl, shortUrl2);
    }
}
