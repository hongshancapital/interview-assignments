package com.sequoia.domain.service.impl;

import com.sequoia.domain.service.IShortDomainService;
import com.sequoia.domain.service.IUrlCacheService;
import com.sequoia.domain.service.IdGenerator;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SelfIncreaseIdGeneratorTest {
    @Test
    public void testIdGenerate() throws InterruptedException {
        IdGenerator idGenerator = new SelfIncreaseIdGenerator(0);
        assertEquals(1, idGenerator.nextId());

        ForkJoinPool pool = new ForkJoinPool(10);
        IntStream.range(0, 100).parallel()
                .forEach(v -> pool.submit(() -> {
                    idGenerator.nextId();
                }));

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        assertEquals(102, idGenerator.nextId());
    }
}