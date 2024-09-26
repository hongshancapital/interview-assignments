package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.common.EventEnum;
import com.jinblog.shorturl.config.ShortConfiguration;
import com.jinblog.shorturl.entry.Event;
import com.jinblog.shorturl.service.EventHandler;
import com.jinblog.shorturl.service.Generator;
import com.jinblog.shorturl.service.RecyclingStrategy;
import com.jinblog.shorturl.service.Storage;
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
class EventHandlerImplTest {

    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private Generator generator;

    @Autowired
    private RecyclingStrategy recyclingStrategy;

    @Autowired
    private Storage storage;

    @Autowired
    private ShortConfiguration shortConfiguration;
    @Test
    void handler() {
        // 测试添加事件
        String shortUrl = generator.generate();
        eventHandler.handler(new Event(EventEnum.ADD_EVENT, shortUrl));
        assertEquals(shortUrl, recyclingStrategy.popGarbage());
        storage.delete(shortUrl);
        assertNull(recyclingStrategy.popGarbage());

        // 测试get事件
        String shortUrl1 = generator.generate();
        String shortUrl2 = generator.generate();
        eventHandler.handler(new Event(EventEnum.ADD_EVENT, shortUrl1));
        eventHandler.handler(new Event(EventEnum.ADD_EVENT, shortUrl2));
        eventHandler.handler(new Event(EventEnum.GET_EVENT, shortUrl1));
        assertEquals(shortUrl2, recyclingStrategy.popGarbage());
        storage.delete(shortUrl2);
        assertEquals(shortUrl1, recyclingStrategy.popGarbage());
        storage.delete(shortUrl1);
        assertNull(recyclingStrategy.popGarbage());

        // 测试gc事件
        String shortUrl3 = generator.generate();
        eventHandler.handler(new Event(EventEnum.ADD_EVENT, shortUrl3));
        eventHandler.handler(new Event(EventEnum.GC_EVENT, null));
        assertNull(recyclingStrategy.popGarbage());

        // 测试添加事件
        String shortUrl5 = generator.generate();
        // 调低内存清理的阈值
        double startRecyclingStrategyMemoryPercent = shortConfiguration.getStartRecyclingStrategyMemoryPercent();
        shortConfiguration.setStartRecyclingStrategyMemoryPercent(0);
        // 增加事件会自动触发内存回收
        eventHandler.handler(new Event(EventEnum.ADD_EVENT, shortUrl5));
        assertNull(storage.find(shortUrl5));
        assertNull(recyclingStrategy.popGarbage());
        shortConfiguration.setStartRecyclingStrategyMemoryPercent(startRecyclingStrategyMemoryPercent);
    }

    @Test
    void gc() {
        double startRecyclingStrategyMemoryPercent = shortConfiguration.getStartRecyclingStrategyMemoryPercent();
        // 测试gc事件
        eventHandler.handler(new Event(EventEnum.ADD_EVENT, generator.generate()));
        eventHandler.handler(new Event(EventEnum.ADD_EVENT, generator.generate()));
        eventHandler.handler(new Event(EventEnum.ADD_EVENT, generator.generate()));
        shortConfiguration.setStartRecyclingStrategyMemoryPercent(0);
        eventHandler.handler(new Event(EventEnum.GC_EVENT, null));
        shortConfiguration.setStartRecyclingStrategyMemoryPercent(startRecyclingStrategyMemoryPercent);
        assertNull(recyclingStrategy.popGarbage());
    }

    @Test
    void testToString() {
        eventHandler.toString();
    }
}