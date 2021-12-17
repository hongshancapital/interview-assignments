package com.scdt.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

/**
 * Class
 *
 * @Author: lenovo
 * @since: 2021-12-16 20:26
 */
public class LRUCacheTest {
    @Mock
    LinkedHashMap<String, String> map;
    @InjectMocks
    LRUCache<String,String> lRUCache;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGet() throws Exception {
        String result = lRUCache.get(new String());
        Assert.assertEquals(null, result);
    }

    @Test
    public void testPut() throws Exception {
        lRUCache.put(new String(), "123");
    }

    @Test
    public void testClear() throws Exception {
        lRUCache.clear();
    }

    @Test
    public void testSize() throws Exception {
        int result = lRUCache.size();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testContainsStringey() throws Exception {
        boolean result = lRUCache.containsKey("key");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Map.Entry<String, String>> result = lRUCache.getAll();
        Assert.assertEquals(new ArrayList<>(), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme