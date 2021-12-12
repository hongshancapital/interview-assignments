package com.scdt.cache.lru;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LRUCacheTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        LRUCache cache = new LRUCache();
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);

        assertTrue((int)cache.get(1) == 1 && (int)cache.get(2) == 2 && (int)cache.get(4) == 4);
    }

}