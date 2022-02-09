package org.example;

import org.example.model.UrlCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

public class UrlCacheTest {

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
    @DisplayName("正常添加后获取正常")
    public void add_test() {
        String shortCode = "abcd";
        String longUrl = "http://www.baidu.com";
        UrlCache.add(shortCode, longUrl);

        Assertions.assertEquals(shortCode, UrlCache.getShortCode(longUrl));
        Assertions.assertEquals(longUrl, UrlCache.getLongUrl(shortCode));
    }

    @Test
    @DisplayName("正常添加后移除成功")
    public void remove_test1() {
        String shortCode = "abcd";
        String longUrl = "http://www.baidu.com";
        UrlCache.add(shortCode, longUrl);
        UrlCache.remove(longUrl);

        Assertions.assertNull(UrlCache.getShortCode(longUrl));
        Assertions.assertNull(UrlCache.getLongUrl(shortCode));
    }

    @Test
    @DisplayName("正常添加后，移除其他的key")
    public void remove_test2() {
        String shortCode = "abcd";
        String longUrl = "http://www.baidu.com";
        UrlCache.add(shortCode, longUrl);
        UrlCache.remove("http://google.com");

        Assertions.assertEquals(shortCode, UrlCache.getShortCode(longUrl));
        Assertions.assertEquals(longUrl, UrlCache.getLongUrl(shortCode));
    }
}
