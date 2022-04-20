package com.shortlink.util;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumericConvertUtilTest {

    @Test
    public void testToOtherNumberSystem() {
        Assert.assertNotNull(NumericConvertUtil.toOtherNumberSystem(1244243534436L, 62));
    }

    @Test
    public void testToDecimalNumber() {
        Assert.assertNotNull(NumericConvertUtil.toDecimalNumber("0lU994jO", 62));
    }
}
