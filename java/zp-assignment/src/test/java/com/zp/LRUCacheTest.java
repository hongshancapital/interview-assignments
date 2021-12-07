package com.zp;

import com.zp.cache.LRUCache;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LRUCacheTest {

    @Test
    public void capacityTest() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("1", "11");
        assertEquals(cache.get("1"), "11");
        cache.put("2", "22");
        assertEquals(cache.get("2"), "22");
        cache.put("3", "33");
        assertEquals(cache.get("3"), "33");
        cache.put("4", "44");
        assertEquals(cache.get("4"), "44");
        assertEquals(cache.get("1"), null);

    }
}
