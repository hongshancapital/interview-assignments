package com.jinblog.shorturl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

// 指定profile
@Profile("default")
@SpringBootTest
// 使用spring的测试框架
@ExtendWith(SpringExtension.class)
class StorageHashTest {

    StorageHash urlMapHashMap = new StorageHash();
    @Test
    void save() {
        String shortUrl1 = "su1";
        String longUrl1 = "lu1";
        urlMapHashMap.save(longUrl1, shortUrl1);
        assertEquals(longUrl1, urlMapHashMap.find(shortUrl1));
    }

    @Test
    void find() {
        String shortUrl1 = "su1";
        String longUrl1 = "lu1";
        urlMapHashMap.save(longUrl1, shortUrl1);
        assertEquals(longUrl1, urlMapHashMap.find(shortUrl1));
        assertNull(urlMapHashMap.find("xxx"));
    }

    @Test
    void delete() {
        String shortUrl1 = "su1";
        String longUrl1 = "lu1";
        urlMapHashMap.save(longUrl1, shortUrl1);
        assertEquals(longUrl1, urlMapHashMap.find(shortUrl1));
        assertNull(urlMapHashMap.find("xxx"));
        urlMapHashMap.delete(shortUrl1);
        assertNull(urlMapHashMap.find(shortUrl1));
    }
}