package com.mortimer.shortenurl.component.cache;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class LocalMemoryUrlMappingCacheTest {
    @Test
    public void test() {
        LocalMemoryUrlMappingCache cache = new LocalMemoryUrlMappingCache(100);
        cache.put("aaa", "bbb");
        Assertions.assertEquals("bbb", cache.get("aaa"));

        cache = new LocalMemoryUrlMappingCache(1000, 1, TimeUnit.SECONDS);
        cache.put("111", "222");
        Assertions.assertEquals("222", cache.get("111"));
        try {
            Thread.sleep(2 * 1000);
            Assertions.assertNull(cache.get("111"));
        } catch (InterruptedException e) {
            log.error("等等缓存失效时被打断");
        }
    }
}
