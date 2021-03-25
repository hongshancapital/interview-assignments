package com.shorturl;

import org.junit.Test;

import static com.shorturl.utils.NumberUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by ruohanpan on 21/3/23.
 */
public class NumberUtilsTest {

    @Test
    public void testDecimal2base62() {
        assertEquals("0", decimal2base62(0L));
        assertEquals("A", decimal2base62(36L));
    }

    @Test
    public void testBase62ToDecimal() {
        assertEquals(Long.valueOf(0l), base62ToDecimal("0"));
        assertEquals(Long.valueOf(36L), base62ToDecimal("A"));
    }

    @Test
    public void testAscii2integer() {
        assertEquals(0, ascii2integer('0'));
        assertEquals(10, ascii2integer('a'));
        assertEquals(35, ascii2integer('z'));
        assertEquals(36, ascii2integer('A'));
        assertEquals(61, ascii2integer('Z'));
    }

    @Test
    public void test() {
        System.out.println(base62ToDecimal("8g"));
        System.out.println(base62ToDecimal("gw"));
    }

}