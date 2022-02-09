package com.scdt.cache.lru;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CacheSegmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        CacheSegment cacheSegment = new CacheSegment();
        cacheSegment.put(1, 1);
        cacheSegment.put(2, 2);
        cacheSegment.put(3, 3);
        cacheSegment.put(4, 4);
        cacheSegment.put(5, 5);

        assertEquals(cacheSegment.size(), 5);

        assertTrue((int)cacheSegment.get(1) == 1 && (int)cacheSegment.get(2) == 2 && (int)cacheSegment.get(4) == 4);

    }
}