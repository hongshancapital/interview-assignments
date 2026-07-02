package com.wuaping.shortlink.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 缓存单元测试
 *
 * @author Aping
 * @since 2022/3/20 14:36
 */
@SpringBootTest
public class ShortLinkCacheTest {

    @Autowired
    private ShortLinkCache shortLinkCache;

    @Test
    void cacheTest() {

        String cacheName = "KEY_SHORT_LINK";
        String cacheKey = "my_key";
        String cacheValue = "my_value";

        shortLinkCache.putValue(cacheName, cacheKey, cacheValue);


        String value = shortLinkCache.getValue(cacheName, cacheKey);
        System.out.println("value: " + value);

        if (!cacheValue.equals(value)){
            throw new RuntimeException("toShortLink test failure");
        }

        System.out.println("test pass");
    }

}
