package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.service.Generator;
import com.jinblog.shorturl.service.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

// 指定profile
@Profile("default")
@SpringBootTest
// 使用spring的测试框架
@ExtendWith(SpringExtension.class)
class RecyclingStrategyLRUImplTest {

    private RecyclingStrategyLRUImpl recyclingStrategy = new RecyclingStrategyLRUImpl();

    @Autowired
    private Generator generator;

    @Autowired
    private Storage storage;

    @BeforeEach
    void setUp() {
        recyclingStrategy.setStorage(storage);
    }

    @Test
    void handleAddEvent() {
        String longUrl = "https://baidu.com";
        String shortUrl = generator.generate();
        storage.save(longUrl, shortUrl);
        assertNull(recyclingStrategy.popGarbage());
        recyclingStrategy.handleAddEvent(shortUrl);
        assertEquals(shortUrl, recyclingStrategy.popGarbage());
        storage.delete(shortUrl);
    }

    @Test
    void handleGetEvent() {
        String longUrl = "https://baidu.com";
        String shortUrl = generator.generate();
        storage.save(longUrl, shortUrl);
        assertNull(recyclingStrategy.popGarbage());
        recyclingStrategy.handleGetEvent(shortUrl);
        assertNull(recyclingStrategy.popGarbage());
        recyclingStrategy.handleAddEvent(shortUrl);
        assertEquals(shortUrl, recyclingStrategy.popGarbage());
        storage.delete(shortUrl);
    }

    @Test
    void remove() {
        // 测试删除-单个节点
        String longUrl = "https://baidu.com";
        String shortUrl = generator.generate();
        storage.save(longUrl, shortUrl);
        assertNull(recyclingStrategy.popGarbage());
        recyclingStrategy.handleAddEvent(shortUrl);
        assertEquals(shortUrl, recyclingStrategy.popGarbage());
        storage.delete(shortUrl);
        assertNull(storage.find(shortUrl));
        assertNull(recyclingStrategy.popGarbage());

        // 测试自动轮换-多个节点
        String longUrl1 = "https://baidu.com";
        String shortUrl1 = generator.generate();
        storage.save(longUrl1, shortUrl1);
        recyclingStrategy.handleAddEvent(shortUrl1);

        String longUrl2 = "https://baidu.com";
        String shortUrl2 = generator.generate();
        storage.save(longUrl2, shortUrl2);
        recyclingStrategy.handleAddEvent(shortUrl2);

        String longUrl3 = "https://baidu.com";
        String shortUrl3 = generator.generate();
        storage.save(longUrl3, shortUrl3);
        recyclingStrategy.handleAddEvent(shortUrl3);

        // 应该删除的是shortUrl1
        assertEquals(shortUrl1, recyclingStrategy.popGarbage());
        storage.delete(shortUrl1);
        assertNull(storage.find(shortUrl1));

        // 处理获取事件，这样2就跑到3前面了，再删除就是删除3
        recyclingStrategy.handleGetEvent(shortUrl2);
        assertEquals(shortUrl3, recyclingStrategy.popGarbage());
        storage.delete(shortUrl3);
        assertNull(storage.find(shortUrl3));
        assertEquals(longUrl2, storage.find(shortUrl2));

        // 最后删除2
        assertEquals(shortUrl2, recyclingStrategy.popGarbage());
        storage.delete(shortUrl2);
        assertNull(storage.find(shortUrl2));
    }

    @Test
    void moveToHead() {
// 测试自动轮换-多个节点
        String longUrl1 = "https://baidu.com";
        String shortUrl1 = generator.generate();
        storage.save(longUrl1, shortUrl1);
        recyclingStrategy.handleAddEvent(shortUrl1);

        String longUrl2 = "https://baidu.com";
        String shortUrl2 = generator.generate();
        storage.save(longUrl2, shortUrl2);
        recyclingStrategy.handleAddEvent(shortUrl2);

        String longUrl3 = "https://baidu.com";
        String shortUrl3 = generator.generate();
        storage.save(longUrl3, shortUrl3);
        recyclingStrategy.handleAddEvent(shortUrl3);

        // 应该删除的是shortUrl1
        assertEquals(shortUrl1, recyclingStrategy.popGarbage());
        storage.delete(shortUrl1);
        assertNull(storage.find(shortUrl1));

        // 处理获取事件，这样2就跑到3前面了，再删除就是删除3
        recyclingStrategy.handleGetEvent(shortUrl2);
        assertEquals(shortUrl3, recyclingStrategy.popGarbage());
        storage.delete(shortUrl3);
        assertNull(storage.find(shortUrl3));
        assertEquals(longUrl2, storage.find(shortUrl2));

        // 最后删除2
        assertEquals(shortUrl2, recyclingStrategy.popGarbage());
        storage.delete(shortUrl2);
        assertNull(storage.find(shortUrl2));
    }

    @Test
    void popGarbage() {
        String longUrl = "https://baidu.com";
        String shortUrl = generator.generate();
        storage.save(longUrl, shortUrl);
        assertNull(recyclingStrategy.popGarbage());
        recyclingStrategy.handleAddEvent(longUrl);
        assertNotNull(shortUrl, recyclingStrategy.popGarbage());
        storage.delete(shortUrl);
    }

    @Test
    void setStorage() {
        recyclingStrategy.setStorage(storage);
    }
}