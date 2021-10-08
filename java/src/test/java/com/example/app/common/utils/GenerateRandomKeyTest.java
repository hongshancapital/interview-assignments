package com.example.app.common.utils;

import com.example.app.AppApplicationTests;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author voidm
 * @date 2021/9/24
 */
public class GenerateRandomKeyTest extends AppApplicationTests {

    @Test
    public void encode62() {
        int num = 123;
        String encode62 = GenerateRandomKey.encode62(num);
        log.info(String.format("进制转换测试 10转62  >>>  %d/%s", num, encode62));

        Assert.assertEquals(GenerateRandomKey.encode62(0),"0");
        Assert.assertEquals(GenerateRandomKey.encode62(61),"z");

    }
}