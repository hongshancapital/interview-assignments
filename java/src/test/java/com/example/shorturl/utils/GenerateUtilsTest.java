package com.example.shorturl.utils;

import com.example.shorturl.ShorturlApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class GenerateUtilsTest {

    @Test
    public void formatUrlCode() {
        String origin = "1dR3sd";
        String expect = "001dR3sd";
        String result = GenerateUtils.formatUrlCode(origin, 8);
        Assert.assertEquals(result, expect);
    }

    @Test
    public void longToSixtyTwo() {
        String randomStr1 = GenerateUtils.longToSixtyTwo(0L);
        Assert.assertEquals(randomStr1, "0");

        String randomStr2 = GenerateUtils.longToSixtyTwo(-100L);
        Assert.assertEquals(randomStr2, "0");

        String randomStr = GenerateUtils.longToSixtyTwo(132232L);
        Assert.assertNotNull(randomStr);
    }

    @Test
    public void sixtyTwoToLong() {
        String sixtyToLong = "zLlte7WK";
        long sixtyLong = GenerateUtils.sixtyTwoToLong(sixtyToLong);
        String val = GenerateUtils.longToSixtyTwo(sixtyLong);
        Assert.assertEquals(sixtyToLong, val);
    }

    @Test
    public void getRandomNumber() {
        Long randomNumber = GenerateUtils.getRandomNumber(1L, 100000000L);
        Assert.assertNotNull(randomNumber);
        Assert.assertTrue(randomNumber > 0);
    }

    @Test
    public void testGetRandomNumber() {
        Integer number = GenerateUtils.getRandomNumber(500);
        Assert.assertNotNull(number);
        Assert.assertTrue(number <= 500);
    }
}