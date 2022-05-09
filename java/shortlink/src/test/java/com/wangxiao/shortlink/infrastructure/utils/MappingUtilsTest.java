package com.wangxiao.shortlink.infrastructure.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MappingUtilsTest {

    @Test
    void testHashing() {
        Long result = MappingUtils.hashing("text");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testEncodeBase62() {
        String result = MappingUtils.encodeBase62(Long.valueOf(1));
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

