package com.scdt.interview.url;

import com.scdt.interview.url.utils.NumberUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: lijin
 * @date: 2021年10月10日
 */
public class NumberUtilTests {

    @Test
    public void dec2AnyWithValidRadix(){
        long dec = 62 * 62;
        String s1 = NumberUtil.dec2Any(dec, 2);
        String s2 = NumberUtil.dec2Any(dec, 7);
        String s3 = NumberUtil.dec2Any(dec, 62);
        String s4= NumberUtil.dec2Any(dec, 10);

        Assertions.assertEquals("111100000100", s1);
        Assertions.assertEquals("14131", s2);
        Assertions.assertEquals("100", s3);
        Assertions.assertEquals("3844", s4);

    }

    @Test
    public void dec2AnyWithOutOfRadix(){
        long dec = 62 * 62;
        String s1 = NumberUtil.dec2Any(dec, -4);
        String s2 = NumberUtil.dec2Any(dec, 1);
        String s3 = NumberUtil.dec2Any(dec, 63);

        Assertions.assertEquals("111100000100", s1);
        Assertions.assertTrue(s1.equals(s2));
        Assertions.assertTrue(s2.equals(s3));

    }


    @Test
    public void dec2AnyWithNegativeDec(){
        String s1 = NumberUtil.dec2Any(-62*62, 62);

        Assertions.assertEquals("-100", s1);

    }
}
