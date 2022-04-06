package com.domain.urlshortener.core;

import com.domain.urlshortener.config.ConfigProperties;
import com.domain.urlshortener.core.cache.MappingCacheWriteExecutor;
import com.domain.urlshortener.core.cache.MemoryMappingCache;
import com.domain.urlshortener.core.sequence.MemorySequenceGenerator;
import com.domain.urlshortener.core.store.MemoryMappingStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 2:10
 */
public class ShortenManagerTest {

    private ShortenManager shortenManager;

    @BeforeEach
    public void setUp() {
        shortenManager = new ShortenManager(new MemorySequenceGenerator(), new MemoryMappingStore(20000L),
                new MemoryMappingCache(10000L, 30), new MappingCacheWriteExecutor(10000));
    }

    @Test
    public void test_shorten() {
       String url = "http://xx.com";
       String alias = shortenManager.shorten(url);
       Assertions.assertEquals("G8", alias);
    }

    @Test
    public void test_getUrl() {

        test_shorten();

        String alias = "G8";
        String url = shortenManager.getUrl(alias);
        Assertions.assertEquals("http://xx.com", url);
    }

}
