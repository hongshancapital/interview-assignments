package com.scdt.china.shortdomain.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: CZ
 * @Date: 2022/1/22 20:34
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConversionUtilTest {

    @Test
    public void encode10To62() {
        Assert.assertEquals("0000001", ConversionUtil.encode10To62(1));
        Assert.assertEquals("0000ABC", ConversionUtil.encode10To62(39134));
    }

    @Test
    public void decode62To10() {
        Assert.assertEquals(1314, ConversionUtil.decode62To10(ConversionUtil.encode10To62(1314)));
    }
}