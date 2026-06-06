package com.jinblog.shorturl.config;

import com.jinblog.shorturl.common.GenerateModeEnum;
import com.jinblog.shorturl.common.RecyclingStrategyEnum;
import com.jinblog.shorturl.common.UrlMapEnum;
import com.jinblog.shorturl.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;

// 指定profile
@Profile("default")
@SpringBootTest
// 使用spring的测试框架
@ExtendWith(SpringExtension.class)
class ShortConfigurationTest {

    private ShortConfiguration shortConfiguration;
    @BeforeEach
    public void before() {
        if (shortConfiguration == null) {
            shortConfiguration = new ShortConfiguration();
        }
    }

    @Test
    void setGenerateMode() {
        shortConfiguration.setGenerateMode(GenerateModeEnum.CHARACTER);
        assertEquals(shortConfiguration.getGenerateMode(), GenerateModeEnum.CHARACTER);
    }

    @Test
    void getGenerateMode() {
        shortConfiguration.setGenerateMode(GenerateModeEnum.CHARACTER);
        assertEquals(shortConfiguration.getGenerateMode(), GenerateModeEnum.CHARACTER);
    }

    @Test
    void setRecyclingStrategyMode() {
        shortConfiguration.setRecyclingStrategyMode(RecyclingStrategyEnum.LRU);
        assertEquals(shortConfiguration.getRecyclingStrategyMode(), RecyclingStrategyEnum.LRU);
    }

    @Test
    void getRecyclingStrategyMode() {
        shortConfiguration.setRecyclingStrategyMode(RecyclingStrategyEnum.LRU);
        assertEquals(shortConfiguration.getRecyclingStrategyMode(), RecyclingStrategyEnum.LRU);
    }

    @Test
    void setUrlMapMode() {
        shortConfiguration.setUrlMapMode(UrlMapEnum.HASH);
        assertEquals(shortConfiguration.getUrlMapMode(), UrlMapEnum.HASH);
    }

    @Test
    void getUrlMapMode() {
        shortConfiguration.setUrlMapMode(UrlMapEnum.HASH);
        assertEquals(shortConfiguration.getUrlMapMode(), UrlMapEnum.HASH);
    }

    @Test
    void getUrlMaxLen() {
        shortConfiguration.setUrlMaxLen(888);
        assertEquals(shortConfiguration.getUrlMaxLen(), 888);
    }

    @Test
    void setUrlMaxLen() {
        shortConfiguration.setUrlMaxLen(888);
        assertEquals(shortConfiguration.getUrlMaxLen(), 888);
    }

    @Test
    void setMaxPendingEvent() {
        shortConfiguration.setMaxPendingEvent(888);
        assertEquals(shortConfiguration.getMaxPendingEvent(), 888);
    }

    @Test
    void getMaxPendingEvent() {
        shortConfiguration.setMaxPendingEvent(888);
        assertEquals(shortConfiguration.getMaxPendingEvent(), 888);
    }

    @Test
    void setHighestMemoryPercent() {
        shortConfiguration.setHighestMemoryPercent(888);
        assertEquals(shortConfiguration.getHighestMemoryPercent(), 888);
    }

    @Test
    void getHighestMemoryPercent() {
        shortConfiguration.setHighestMemoryPercent(888);
        assertEquals(shortConfiguration.getHighestMemoryPercent(), 888);
    }

    @Test
    void setStartRecyclingStrategyMemoryPercent() {
        shortConfiguration.setStartRecyclingStrategyMemoryPercent(888);
        assertEquals(shortConfiguration.getStartRecyclingStrategyMemoryPercent(), 888);
    }

    @Test
    void getStartRecyclingStrategyMemoryPercent() {
        shortConfiguration.setStartRecyclingStrategyMemoryPercent(888);
        assertEquals(shortConfiguration.getStartRecyclingStrategyMemoryPercent(), 888);
    }

    @Test
    void setShortUrlDomain() {
        shortConfiguration.setShortUrlDomain("https://jin.com");
        assertEquals(shortConfiguration.getShortUrlDomain(), "https://jin.com");
    }

    @Test
    void getShortUrlDomain() {
        shortConfiguration.setShortUrlDomain("https://jin.com");
        assertEquals(shortConfiguration.getShortUrlDomain(), "https://jin.com");
    }

    @Test
    void afterPropertiesSet() throws Exception {
        shortConfiguration.setShortUrlDomain("https://jin.com");
        shortConfiguration.afterPropertiesSet();
        assertEquals(shortConfiguration.getShortUrlDomain(), "https://jin.com/");
        shortConfiguration.setShortUrlDomain(null);
        assertThrows(RuntimeException.class, () -> shortConfiguration.afterPropertiesSet());
        shortConfiguration.setShortUrlDomain("htps://jin.com");
        assertThrows(RuntimeException.class, () -> shortConfiguration.afterPropertiesSet());
    }

    @Test
    void generator() {
        shortConfiguration.setGenerateMode(null);
        assertThrows(RuntimeException.class, () -> shortConfiguration.generator());
        shortConfiguration.setGenerateMode(GenerateModeEnum.CHARACTER);
        assertEquals(CharacterGenerator.class, shortConfiguration.generator().getClass());

        shortConfiguration.setGenerateMode(GenerateModeEnum.INTEGER);
        assertEquals(IntegerGenerator.class, shortConfiguration.generator().getClass());
    }

    @Test
    void urlMap() {
        shortConfiguration.setUrlMapMode(null);
        assertThrows(RuntimeException.class, () -> shortConfiguration.urlMap());
        shortConfiguration.setUrlMapMode(UrlMapEnum.HASH);
        assertEquals(StorageHash.class, shortConfiguration.urlMap().getClass());
        shortConfiguration.setUrlMapMode(UrlMapEnum.TRIE);
        assertEquals(StorageTrie.class, shortConfiguration.urlMap().getClass());
    }

    @Test
    void recyclingStrategy() {
        shortConfiguration.setRecyclingStrategyMode(null);
        assertThrows(RuntimeException.class, () -> shortConfiguration.recyclingStrategy());
        shortConfiguration.setRecyclingStrategyMode(RecyclingStrategyEnum.LRU);
        assertEquals(RecyclingStrategyLRUImpl.class, shortConfiguration.recyclingStrategy().getClass());

        shortConfiguration.setRecyclingStrategyMode(RecyclingStrategyEnum.NONE);
        assertEquals(RecyclingStrategyNoneImpl.class, shortConfiguration.recyclingStrategy().getClass());
    }

    @Test
    void shortUrlEventHandlerPool() {
        assertEquals(ThreadPoolExecutor.class, shortConfiguration.shortUrlEventHandlerPool().getClass());
    }

    @Autowired
    ShortConfiguration shortConfigurationAutowired;
    @Test
    void getInstance() {
        assertNotNull(ShortConfiguration.getInstance());
        assertEquals(shortConfigurationAutowired, ShortConfiguration.getInstance());
    }
}