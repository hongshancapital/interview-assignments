package com.hello.tinyurl.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TinyUrlCacheDaoTest {

    @Resource(name = "tinyUrlCacheDao")
    private TinyUrlCacheDao tinyUrlDao;

    @Test
    void getOriginalUrlByTinyUrl() throws Exception {
        String tinyUrl = tinyUrlDao.saveOriginalUrl("hello");
        String original = tinyUrlDao.getOriginalUrl(tinyUrl);
        assertEquals(original, "hello");
    }

    @Test
    void sameOriginalReturnSameTinyUrl() throws Exception {
        String tinyUrl1 = tinyUrlDao.saveOriginalUrl("test");
        String tinyUrl2 = tinyUrlDao.saveOriginalUrl("test");
        assertEquals(tinyUrl1, tinyUrl2);
    }

    @Test
    void emptyId() {
        try {
            tinyUrlDao.getOriginalUrl(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("input tinyUrl empty."));
        }
    }

    @Test
    void emptyString() {
        try {
            tinyUrlDao.saveOriginalUrl("");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("input originalUrl empty."));
        }
    }

    @Test
    void emptyCache() throws Exception {
        assertNull(tinyUrlDao.getOriginalUrl("fghjk"));
    }

}