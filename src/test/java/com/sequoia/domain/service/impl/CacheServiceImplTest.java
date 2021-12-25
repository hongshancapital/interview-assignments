package com.sequoia.domain.service.impl;

import com.sequoia.domain.service.IUrlCacheService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CacheServiceImplTest {
    @Test
    public void testCache() throws InterruptedException {
        IUrlCacheService urlCacheService = new UrlCacheServiceImpl(8, 8);

        ForkJoinPool pool = new ForkJoinPool(10);
        LongStream.range(0L, 8L).parallel()
                .forEach(v -> pool.submit(() -> {
                    urlCacheService.put(v, String.valueOf(v));
                }));
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        //检查缓存值
        assertEquals("0", urlCacheService.get(0L));
        assertEquals("1", urlCacheService.get(1L));
        assertEquals("2", urlCacheService.get(2L));
        assertEquals("3", urlCacheService.get(3L));
        assertEquals("4", urlCacheService.get(4L));
        assertEquals("5", urlCacheService.get(5L));
        assertEquals("6", urlCacheService.get(6L));
        assertEquals("7", urlCacheService.get(7L));
        urlCacheService.put(9L, "9");
        //最早读取值0被淘汰
        assertNotEquals("0", urlCacheService.get(0L));
        //刷新1的值，2变成最早读取的值
        assertEquals("1", urlCacheService.get(1L));
        urlCacheService.put(10L, "9");
        //2被淘汰
        assertNotEquals("2", urlCacheService.get(2L));
    }
}