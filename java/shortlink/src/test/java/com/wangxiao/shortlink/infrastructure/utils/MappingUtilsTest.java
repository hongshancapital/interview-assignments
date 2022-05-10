package com.wangxiao.shortlink.infrastructure.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class MappingUtilsTest {

    @Test
    void testHashing() {
        Long result = MappingUtils.hashing(UUID.randomUUID().toString());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(Double.compare(Math.pow(62, 6), result.doubleValue()) > 0);
    }

    @Test
    void testEncodeBase62() {
        Long hash = MappingUtils.hashing(UUID.randomUUID().toString());
        String result = MappingUtils.encodeBase62WithLength(hash, 6);
        Assertions.assertTrue(result.length() == 6);
        String result1 = MappingUtils.encodeBase62WithLength(1L, 6);
        Assertions.assertEquals(result1, "AAAAAB");
    }
}

