package com.wangxiao.shortlink.infrastructure.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MachineIdUtilsTest {

    @Test
    void testGetMachineId() {
        String result = MachineIdUtils.getMachineId("01abcdef");
        Assertions.assertEquals("01", result);
    }

    @Test
    void testGetShortCode() {
        String result = MachineIdUtils.getShortCode("01abcdef");
        Assertions.assertEquals("abcdef", result);
    }

    @Test
    void testCombine() {
        String result = MachineIdUtils.combine("01", "abcdef");
        Assertions.assertEquals("01abcdef", result);
    }
}

