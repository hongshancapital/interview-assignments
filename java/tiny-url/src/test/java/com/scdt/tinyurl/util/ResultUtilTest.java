package com.scdt.tinyurl.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultUtilTest {

    @Test
    void success() {
        Assert.assertEquals("200",ResultUtil.success().getCode());
        Assert.assertEquals("OK",ResultUtil.success("OK").getData());
        Assert.assertEquals("成功",ResultUtil.success("OK","成功").getMsg());

    }


    @Test
    void error() {
        Assert.assertEquals("未知错误",ResultUtil.error("401","未知错误").getMsg());
        Assert.assertEquals("401",ResultUtil.error("401","未知错误").getCode());
        Assert.assertEquals("401",ResultUtil.error("401","未知错误","").getCode());


    }
}