package com.hello.tinyurl.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConvertUtilTest {

    @Test
    void emptyStringTest() {
        try {
            ConvertUtil.string2Number(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("input string is empty."));
        }
    }

    @Test
    void string2Number() throws Exception {
        String s = "4dS";
        long n = ConvertUtil.string2Number(s);
        long success = 2 * 62 * 62 + 62;
        assertEquals(n, success);
    }

    @Test
    void inputIllegalCharacter() {
        try {
            String s = "123^32";
            long n = ConvertUtil.string2Number(s);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("contains illegal character '^'."));
        }
    }

    @Test
    void num2StringInputEmpty() {  //7750
        try {
            Long id = null;
            String s = ConvertUtil.num2String(id);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("input id is empty."));
        }
    }

    @Test
    void num2String() throws Exception {  //7750
        long n = 34523453L;
        String s = ConvertUtil.num2String(n);
        System.out.println(s);

        assertEquals(s, "SSS4uZgi");
    }

    @Test
    void stringLongerThen8Characters() {  //7750
        try {
            String s = "aaaaaaS4uZgi";
            long n = ConvertUtil.string2Number(s);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("input string aaaaaaS4uZgi longer then 8 characters."));
        }
    }

    @Test
    void idLongerThenMaxValue() {  //7750
        try {
            long n = 218340105584897L;
            String s = ConvertUtil.num2String(n);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("out of maximum tiny url length."));
        }
    }


}