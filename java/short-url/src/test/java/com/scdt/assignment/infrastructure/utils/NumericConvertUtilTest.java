package com.scdt.assignment.infrastructure.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumericConvertUtilTest {

    @Test
    public void test() {
        String temp = "sfjshuiujnkjnkjmkmkaosfjshuiujnkjnkjmkmkao";
        assertThrows(NumberFormatException.class,
                () -> NumericConvertUtil.toDecimalNumber("sfjshuiujnkjnkjmkmkaosfjshuiujnkjnkjmkmkao"));

        assertThrows(NullPointerException.class,
                () -> NumericConvertUtil.toDecimalNumber(null));

        assertThrows(IllegalArgumentException.class,
                () -> NumericConvertUtil.toDecimalNumber(""));

        temp = "0";
        assertEquals(NumericConvertUtil.toOtherNumber(NumericConvertUtil.toDecimalNumber(temp)), temp);

        temp = "1";
        assertEquals(NumericConvertUtil.toOtherNumber(NumericConvertUtil.toDecimalNumber(temp)), temp);

        temp = "123dsffsdde";
        assertEquals(NumericConvertUtil.toOtherNumber(NumericConvertUtil.toDecimalNumber(temp)), temp);

    }
}