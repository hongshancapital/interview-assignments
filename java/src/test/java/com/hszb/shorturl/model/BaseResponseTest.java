package com.hszb.shorturl.model;

import com.hszb.shorturl.model.response.BaseResponse;
import org.junit.Test;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/24 10:16 上午
 * @Version: 1.0
 * @Description:
 */

public class BaseResponseTest {

    BaseResponse baseResponse = new BaseResponse();

    @Test
    public void testResult () {
        baseResponse.setResultCode(null);
        baseResponse.checkSuccess();
    }

    @Test
    public void testResult2 () {
        baseResponse.setResultCode(BaseResponse.ResponseResult.SUCCESS.resultCode);
        baseResponse.checkSuccess();
    }

    @Test
    public void testResult3 () {
        baseResponse.setResultCode(BaseResponse.ResponseResult.ERROR.resultCode);
        baseResponse.checkSuccess();
    }
}
