package com.dblones.shortlink.util;

import org.junit.Test;

public class NumberUtilsTest {

    @Test
    public void compressNumber(){
        long num = 2394278347l;
        String result = NumberUtils.compressNumber(num);
        System.out.println(result);
    }
}
