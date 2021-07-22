package com.hello.tinyurl.dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author: Shuai
 * @date: 2021-7-20 19:24
 * @description:
 */
@SpringBootTest
public class TinyUrlCacheDaoExceptionTest {

    @Resource(name = "tinyUrlCacheDao")
    private TinyUrlCacheDao tinyUrlDao;

    @MockBean
    private CacheManager cacheManager;

    @Test
    public void testSaveOriginalUrlThrowable() {
        String CACHE_KEY = "CACHE_KEY";
        try {
            Mockito.when(cacheManager.getCache(CACHE_KEY)).thenReturn(null);
            tinyUrlDao.saveOriginalUrl("test");
        } catch (Exception e) {
            assertEquals("Cache must not be null", e.getMessage());
        }
    }

    @Test
    public void testGetOriginalUrlThrowable() {
        String CACHE_KEY = "CACHE_KEY";
        try {
            Mockito.when(cacheManager.getCache(CACHE_KEY)).thenReturn(null);
            tinyUrlDao.getOriginalUrl("fghjk");
        } catch (Exception e) {
            assertEquals("Cache must not be null", e.getMessage());
        }
    }
}
