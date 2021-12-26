package com.sequoia.domain.service.impl;

import com.sequoia.domain.service.IdGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnowFlakeIdGeneratorTest {
    @Test
    public void testIdGenerate() throws InterruptedException {
        IdGenerator idGenerator = new SnowFlakeIdGenerator(1, 1);
        Set<Long> sets = new HashSet<>();
        IntStream.range(0, 10000).parallel()
                .forEach(v -> sets.add(idGenerator.nextId()));
        Thread.sleep(100);
        IntStream.range(0, 10000).parallel()
                .forEach(v -> sets.add(idGenerator.nextId()));
//        assertEquals(2000, sets.size());
    }
}