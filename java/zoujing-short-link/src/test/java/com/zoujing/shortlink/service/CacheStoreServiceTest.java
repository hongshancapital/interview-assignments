package com.zoujing.shortlink.service;

import com.zoujing.shortlink.service.CacheStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheStoreServiceTest {
    @Resource
    private CacheStoreService cacheStoreService;

    @Test
    public void testCacheStoreService() {
        cacheStoreService.put("aaaaaaaa", "xxxxxxxxxxxx.xxx.com");
        cacheStoreService.put("aaaaaaaa", null);
        assertEquals("xxxxxxxxxxxx.xxx.com", cacheStoreService.get("aaaaaaaa"));
        assertNull(cacheStoreService.get(null));
    }
}
