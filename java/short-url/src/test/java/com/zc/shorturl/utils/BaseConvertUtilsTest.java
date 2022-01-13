package com.zc.shorturl.utils;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


public class BaseConvertUtilsTest {
    @Test
    public void test_decimal2Base62() {
        assertEquals("pB", BaseConvertUtils.decimal2Base62(123));
        assertEquals("L", BaseConvertUtils.decimal2Base62(0));
        assertEquals("", BaseConvertUtils.decimal2Base62(-1));
    }
}
