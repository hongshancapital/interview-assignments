package com.sequoia;

import com.sequoia.infrastructure.common.ProducerPromise;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;

/**
 * Descript:
 * File: com.sequoia.TinyUrlApplicationTest
 * Author: daishengkai
 * Date: 2022/3/31
 * Copyright (c) 2022,All Rights Reserved.
 */
@SpringBootTest
public class TinyUrlApplicationTest {

    @Test
    public void context() {

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

}
