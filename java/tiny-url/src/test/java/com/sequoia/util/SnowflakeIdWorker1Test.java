package com.sequoia.util;

import com.sequoia.infrastructure.common.ProducerPromise;
import com.sequoia.infrastructure.service.impl.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.Executors;

/**
 * Descript:
 * File: com.sequoia.util.SnowflakeIdWorker1Test
 * Author: daishengkai
 * Date: 2022/3/30
 * Copyright (c) 2022,All Rights Reserved.
 */
@Slf4j
@SpringBootTest
public class SnowflakeIdWorker1Test {

    @Resource
    private SnowflakeIdWorker snowflakeIdWorker;

    @Test
    public void idGenerateTest() {
        log.info("当前时间:{}", System.currentTimeMillis());
        long startTime = System.nanoTime();
        for (int i = 0; i < 50000; i++) {
            long id = snowflakeIdWorker.nextId(""+i);
            log.info("生成的id: {}", id);
        }
        log.info("生成id耗时: {}", (System.nanoTime()-startTime)/1000000+"ms");
    }

    @Test
    public void setMachineIdTest() {
        log.info("当前时间:{}", System.currentTimeMillis());
        long startTime = System.nanoTime();
        snowflakeIdWorker.setMachineId(10001L);
        for (int i = 0; i < 10; i++) {
            long id = snowflakeIdWorker.nextId(i+"");
            log.info("生成的id: {}", id);
        }
        snowflakeIdWorker.setMachineId(-1L);
        log.info("生成id耗时: {}", (System.nanoTime()-startTime)/1000000+"ms");
    }

    @Test
    public void testcompleteExceptionally() {
        ProducerPromise<String> promise = new ProducerPromise<>(Thread.currentThread());
        promise.shouldProduce();

        promise.shouldProduce();

        Executors.newFixedThreadPool(5).execute(() -> {
            promise.shouldProduce();
        });

        promise.completeExceptionally(new RuntimeException());
    }

//    @BeforeClass
//    public void initBeforetestError() {
//        MemberModifier.stub(PowerMockito.method(InetAddress.class, "getLocalHost")).toThrow(new UnknownHostException());
//    }

    @Test
    public void testError() {
        try {
            long id = snowflakeIdWorker.nextId("");
        } catch (Exception e) {
            log.error("异常", e);
        }

//        MemberModifier.stub(PowerMockito.method(SnowflakeIdWorker.class, "getNowSeconds")).toReturn(-100L);
//        SnowflakeIdWorker.nextId();
    }
}
