package com.david.urlconverter.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterUtilsTest {

    private long maxNum = (long)Math.pow(62,8)-1;

    private String maxResult = "99999999";

    @Test
    public void testConvertNumberToSeed(){
        assertThat(ConverterUtils.convertNumberToSeed(maxNum,62L)).isEqualTo(maxResult);
    }

    @Test
    public void testConvertNumber(){
        assertThat(ConverterUtils.convertNumber(62*62)).isEqualTo("baa");
    }

    @Test
    public void testConstructor(){
        System.out.println(new ConverterUtils());
    }
}
