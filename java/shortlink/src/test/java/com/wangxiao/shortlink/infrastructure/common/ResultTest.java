package com.wangxiao.shortlink.infrastructure.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResultTest {


    @Test
    void testSuccess() {
        Result result = Result.success();
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    void testSuccess2() {
        Result result = Result.success("ok");
        Assertions.assertEquals("ok", result.getData());
    }

    @Test
    void testFail() {
        Result result = Result.fail(ErrorEnum.PARAMS_ILLEGAL);
        Assertions.assertEquals(ErrorEnum.PARAMS_ILLEGAL.getCode(), result.getCode());
    }

    @Test
    void testFail2() {
        Result result = Result.fail(ErrorEnum.PARAMS_ILLEGAL, "msg");
        Assertions.assertEquals("msg", result.getMsg());
    }

    
}

