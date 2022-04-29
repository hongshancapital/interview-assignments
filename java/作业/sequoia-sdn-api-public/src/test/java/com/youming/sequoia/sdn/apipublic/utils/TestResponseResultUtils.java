package com.youming.sequoia.sdn.apipublic.utils;

import com.youming.sequoia.sdn.apipublic.constant.ResponseResultMsgEnum;
import org.junit.jupiter.api.Test;

public class TestResponseResultUtils {


    @Test
    public void test() {
        ResponseResultUtils responseResultUtils = new ResponseResultUtils();
        ResponseResultUtils.getResponseResultStr(ResponseResultMsgEnum.SUCCESS);
        ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.SUCCESS);
        ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.SUCCESS, new Object());
        ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.SUCCESS, new Object(), "附加信息");
        ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.SUCCESS, "附加信息");
    }
}
