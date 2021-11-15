package com.bolord.shorturl.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleIdGeneratorTest {

    final int      concurrent     = 100;
    CountDownLatch countDownLatch = new CountDownLatch(concurrent);

    @Test
    @DisplayName("ID生成器测试")
    void generateId() {
        IdGenerator idGenerator = new SimpleIdGenerator();
        for (long i = 1; i <= concurrent; i++) {
            assertEquals(i, Long.valueOf(idGenerator.generateId()));
        }
    }

    @Test
    @DisplayName("ID生成器并发测试")
    void generateIdConcurrent() {
        IdGenerator idGenerator = new SimpleIdGenerator();
        Set<Long>   set         = new CopyOnWriteArraySet<>();

        for (int i = 0; i < concurrent; i++) {
            new Thread(() -> {
                Long gid = idGenerator.generateId();
                set.add(gid);

                log.info("gid={}, tid={}", gid, Thread.currentThread().getId());

                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            log.info("size={}, concurrent={}", set.size(), concurrent);
            assertEquals(concurrent, set.size());
        }
    }

}
