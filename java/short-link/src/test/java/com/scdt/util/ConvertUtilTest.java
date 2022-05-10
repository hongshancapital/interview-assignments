package com.scdt.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * ConvertUtilTest
 *
 * @author weixiao
 * @date 2022-04-26 18:31
 */
public class ConvertUtilTest {

    @Test
    public void testTenToSixtyTwo() {
        System.out.println(ConvertUtil.tenToSixtyTwo(188347847572799488L, 8));
        Assert.assertEquals("00000000", ConvertUtil.tenToSixtyTwo(0, 8));
        Assert.assertEquals("00Aukyoa", ConvertUtil.tenToSixtyTwo(10000000000L, 8));
        Assert.assertEquals("jnbbgSGrA", ConvertUtil.tenToSixtyTwo(10000000000000000L, 8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTenToSixtyTwoNumberException() {
        Assert.assertEquals("jnbbgSGrA", ConvertUtil.tenToSixtyTwo(-100, 8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTenToSixtyTwoLengthException() {
        Assert.assertEquals("jnbbgSGrA", ConvertUtil.tenToSixtyTwo(100, -8));
    }
}
