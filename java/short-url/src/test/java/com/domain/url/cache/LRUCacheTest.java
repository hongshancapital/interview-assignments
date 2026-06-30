package com.domain.url.cache;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LRUCacheTest {

    @Test
    public void testLRUCacheTest() {
        LRUCache cache = new LRUCache(3);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.get("1");
        cache.get("3");
        cache.get("2");
        cache.get("1");
        cache.put("4", "4");
        cache.put("2", "22");
        cache.put("5", "5");
        Assertions.assertNotNull(cache.toString());
    }

}