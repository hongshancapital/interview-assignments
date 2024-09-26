package com.jinblog.shorturl.entry;

import com.jinblog.shorturl.common.EventEnum;
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
class TaskTest {

    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private Storage storage;

    @Autowired
    private Generator generator;

    @Autowired
    private RecyclingStrategy recyclingStrategy;

    @Test
    void run() {
        String shortUrl = generator.generate();
        Task task = new Task(new Event(EventEnum.ADD_EVENT, shortUrl), eventHandler);
        task.run();
        assertEquals(shortUrl, recyclingStrategy.popGarbage());
    }

    @Test
    void testToString() {
        String shortUrl = generator.generate();
        Task task = new Task(new Event(EventEnum.ADD_EVENT, shortUrl), eventHandler);
        assertEquals(String.class, task.toString().getClass());
    }
}