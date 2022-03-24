package com.shorts.url.service.generator.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LruCacheTests {

    LruCache lruCache = new LruCache(10);

    @Test
    void testPut() {
        Assertions.assertDoesNotThrow(() -> lruCache.put("test", "test"));
    }

    @Test
    void testGet() {
        String key = "test0";
        lruCache.put(key, key);
        Assertions.assertEquals(key, lruCache.get(key));
    }
}
