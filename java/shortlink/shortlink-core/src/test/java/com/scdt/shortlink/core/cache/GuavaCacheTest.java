package com.scdt.shortlink.core.cache;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class GuavaCacheTest {

    private GuavaCache cache = new GuavaCache();

    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Test
    public void get() {
        cache.put(KEY, VALUE);
        String v = cache.get(KEY);
        Assert.assertEquals(v, VALUE);
    }

    @Test
    public void put() {
        cache.put(KEY, VALUE);
        String v = cache.get(KEY);
        Assert.assertEquals(v, VALUE);
    }
}