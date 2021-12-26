package com.sequoia.domain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumericUtilsTest {
    @Test
    public void testBinary62() {
        assertEquals("FFFFFFFF", NumericUtils.toBinary62(146753185720995L));
        assertEquals("0", NumericUtils.toBinary62(0L));
        assertEquals("4GFfc3", NumericUtils.toBinary62(-1L));

        assertEquals(146753185720995L, NumericUtils.toDecimal("FFFFFFFF"));
        assertEquals(0L, NumericUtils.toDecimal("0"));
    }
}