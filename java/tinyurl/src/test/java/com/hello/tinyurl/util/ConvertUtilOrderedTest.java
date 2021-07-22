package com.hello.tinyurl.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConvertUtilOrderedTest {

    @Test
    void emptyStringTest() {
        try {
            UrlConvertUtil.string2Number(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("input string is empty."));
        }
    }

    @Test
    void emptyIDTest() {
        try {
            UrlConvertUtil.num2String(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("input id is empty."));
        }
    }

    @Test
    void string2Number() throws Exception {
        String s = "4dS";
        long n = UrlConvertUtil.string2Number(s);
        long success = 215494;
        assertEquals(n, success);
    }

    @Test
    void inputIllegalCharacterTest() {
        try {
            String s = "123*";
            long n = UrlConvertUtil.string2Number(s);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("contains illegal character '*'."));
        }
    }

    @Test
    void num2String() throws Exception {  //7750
        long n = 34523453L;
        String s = UrlConvertUtil.num2String(n);
        System.out.println(s);

        assertEquals(s, "cu1h3");
    }

    @Test
    void stringLongerThen8Characters() {  //7750
        try {
            String s = "SSSSSSS4uZgi";
            long n = UrlConvertUtil.string2Number(s);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("input string SSSSSSS4uZgi longer then 8 characters."));
        }
    }

    @Test
    void idLongerThenMaxValue() {  //7750
        try {
            long n = 218340105584897L;
            String s = UrlConvertUtil.num2String(n);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("out of maximum tiny url length."));
        }
    }


}