package com.scdt.assignment.infrastructure.utils;


import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SnowflakeIdWorkerTest {

    @Test
    public void testId() {
        Set<Long> set = new HashSet<Long>();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 1000000; i++) {
            long id = idWorker.nextId();
            assertTrue(!set.contains(id));
            set.add(id);
        }
    }

}