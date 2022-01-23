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
public class ResultTest {

    @Test
    public void success() {
        Assert.assertTrue(Result.success("OK").isSuccess());
        Assert.assertEquals(Result.success("OK").getCode(), ReturnCode.SUCCESS.getCode());
        Assert.assertEquals(Result.success("OK").getMessage(), ReturnCode.SUCCESS.getMessage());
        Assert.assertEquals(Result.success("OK").getData(), "OK");
    }

    @Test
    public void fail() {
        Assert.assertFalse(Result.fail(ReturnCode.FAILED.getCode(), ReturnCode.FAILED.getMessage(), "ERROR").isSuccess());
        Assert.assertFalse(Result.fail(ReturnCode.FAILED.getCode(), ReturnCode.FAILED.getMessage()).isSuccess());
        Assert.assertFalse(Result.fail(ReturnCode.FAILED).isSuccess());
        Assert.assertFalse(Result.fail(ReturnCode.FAILED, "ERROR").isSuccess());
        Assert.assertEquals(Result.fail(ReturnCode.FAILED, "ERROR").getData(), "ERROR");
    }


}