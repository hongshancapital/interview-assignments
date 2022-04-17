package com.scdt.shortlink.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author xbhong
 * @date 2022/4/16 23:00
 */
public class ConversionUtilTest {

    private static long Num = 10000000000l;

    @Test
    public void encode62Test() {
        Assert.assertNotNull(ConversionUtil.X.encode62(Num));
    }

    @Test
    public void decode62Test() {
        String str62 = ConversionUtil.X.encode62(Num);
        long long10 = ConversionUtil.X.decode62(str62);
        Assert.assertNotNull(long10);
        System.out.println(str62);
        Assert.assertEquals(long10, Num);
    }

}
