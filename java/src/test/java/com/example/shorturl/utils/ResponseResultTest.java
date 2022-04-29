package com.example.shorturl.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yyp
 * @date 2022/1/16 23:05
 */
public class ResponseResultTest {
    @Test
    public void testResult() {
        ResponseResult<String> sucResult = ResponseResult.success("成功结果");
        Assert.assertEquals(sucResult.getData(), "成功结果");

        ResponseResult<String> failStr = ResponseResult.fail();
        Assert.assertEquals(failStr.getMsg(), "");
        Assert.assertNull(failStr.getData());

        ResponseResult<String> failWithParam = ResponseResult.fail("失败结果");
        Assert.assertEquals(failWithParam.getMsg(), "失败结果");
        Assert.assertNull(failWithParam.getData());
    }
}
