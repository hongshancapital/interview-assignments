package com.scdt.shortenurl.common.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Description
 * @Author chenlipeng
 * @Date 2022/3/6 9:52 下午
 */
public class MemoryUtilsTest {

    @Test
    public void testCheckMemoryEnough() {
        boolean enough = MemoryUtils.checkMemoryEnough();
        Assert.assertTrue(enough);
    }

}
