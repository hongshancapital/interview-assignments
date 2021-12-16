package com.scdt.china.shorturl.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;


public class NumberCodecUtilsTest {

    @Test
    public void encodeAndDecode() {
        Long decodeResult = NumberCodecUtils.decode("0");
        Assertions.assertEquals(0L, (long) decodeResult, "解码不匹配");

        String encodeResult = NumberCodecUtils.encode(0L);
        Assertions.assertEquals("0", encodeResult, "解码不匹配");

        String maxCode = "zzzzzzzz";
        Long maxValue = NumberCodecUtils.decode(maxCode);
        String encodeMaxValue = NumberCodecUtils.encode(maxValue);
        Assertions.assertEquals(maxCode, encodeMaxValue, "解码不匹配");

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            long fromNumber = (long) i * Math.abs(random.nextInt());
            long endNumber = maxValue - fromNumber;
            Assertions.assertEquals(fromNumber, (long) NumberCodecUtils.decode(NumberCodecUtils.encode(fromNumber)), "解码不匹配");
            Assertions.assertEquals(endNumber, (long) NumberCodecUtils.decode(NumberCodecUtils.encode(endNumber)), "解码不匹配");
        }

    }

    @Test
    public void negativeEncode() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NumberCodecUtils.encode(-1L);
        });
    }

    @Test
    public void illegalChars() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NumberCodecUtils.decode("321321ADSAB&");
        });
    }

}