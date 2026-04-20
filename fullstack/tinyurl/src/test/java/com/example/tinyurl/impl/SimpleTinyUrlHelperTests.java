package com.example.tinyurl.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for SimpleTinyUrlHelper
 * @author hermitriver@hotmail.com
 */
public class SimpleTinyUrlHelperTests {
    @Test
    public void mapByte2Char() {
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_CHAR, SimpleTinyUrlHelper.map((byte)-1));
        Assertions.assertEquals('0', SimpleTinyUrlHelper.map((byte)0));
        Assertions.assertEquals('9', SimpleTinyUrlHelper.map((byte)9));
        Assertions.assertEquals('A', SimpleTinyUrlHelper.map((byte)10));
        Assertions.assertEquals('Z', SimpleTinyUrlHelper.map((byte)35));
        Assertions.assertEquals('a', SimpleTinyUrlHelper.map((byte)36));
        Assertions.assertEquals('z', SimpleTinyUrlHelper.map((byte)61));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_CHAR, SimpleTinyUrlHelper.map((byte)62));
    }

    @Test
    public void mapLong2String() {
        Assertions.assertNull(SimpleTinyUrlHelper.map(-1L));
        Assertions.assertEquals("0", SimpleTinyUrlHelper.map(0L));
        Assertions.assertEquals("zzzzzzzz", SimpleTinyUrlHelper.map(218340105584895L));
        Assertions.assertNull(SimpleTinyUrlHelper.map(228340105584895L));
    }

    @Test
    public void mapChar2Byte() {
        Assertions.assertEquals(0, SimpleTinyUrlHelper.map('0'));
        Assertions.assertEquals(9, SimpleTinyUrlHelper.map('9'));
        Assertions.assertEquals(10, SimpleTinyUrlHelper.map('A'));
        Assertions.assertEquals(35, SimpleTinyUrlHelper.map('Z'));
        Assertions.assertEquals(36, SimpleTinyUrlHelper.map('a'));
        Assertions.assertEquals(61, SimpleTinyUrlHelper.map('z'));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_VALUE, SimpleTinyUrlHelper.map('!'));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_VALUE, SimpleTinyUrlHelper.map('中'));
    }

    @Test
    public void mapString2Long() {
        Assertions.assertEquals(0L, SimpleTinyUrlHelper.map("0"));
        Assertions.assertEquals(3978L, SimpleTinyUrlHelper.map("12A"));
        Assertions.assertEquals(218340105584895L, SimpleTinyUrlHelper.map("zzzzzzzz"));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_VALUE, SimpleTinyUrlHelper.map((String) null));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_VALUE, SimpleTinyUrlHelper.map(""));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_VALUE, SimpleTinyUrlHelper.map("!"));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_VALUE, SimpleTinyUrlHelper.map("中文"));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_VALUE, SimpleTinyUrlHelper.map("a!Z"));
        Assertions.assertEquals(SimpleTinyUrlHelper.INVALID_VALUE, SimpleTinyUrlHelper.map("123456789"));
    }
}
