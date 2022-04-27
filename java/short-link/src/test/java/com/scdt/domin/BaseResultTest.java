package com.scdt.domin;

import com.scdt.constant.Consts;
import org.junit.Assert;
import org.junit.Test;

/**
 * BaseResultTest
 *
 * @author weixiao
 * @date 2022-04-26 18:48
 */
public class BaseResultTest {

    @Test
    public void testConstruct() {
        BaseResult success = BaseResult.success("00000000");
        Assert.assertEquals(true, success.isSuccess());
        Assert.assertEquals(Consts.SUCCESS_CODE, success.getCode());
        Assert.assertEquals(Consts.SUCCESS_MSG, success.getMsg());
        Assert.assertEquals("00000000", success.getData());

        BaseResult failure1 = BaseResult.failure(ErrorCode.SERVER_ERROR);
        Assert.assertEquals(false, failure1.isSuccess());
        Assert.assertEquals(ErrorCode.SERVER_ERROR.getCode(), failure1.getCode());
        Assert.assertEquals(ErrorCode.SERVER_ERROR.getMsg(), failure1.getMsg());
        Assert.assertNull(failure1.getData());

        BaseResult failure2 = BaseResult.failure(ErrorCode.SERVER_ERROR, "缓存故障");
        Assert.assertEquals(false, failure2.isSuccess());
        Assert.assertEquals(ErrorCode.SERVER_ERROR.getCode(), failure2.getCode());
        Assert.assertEquals("缓存故障", failure2.getMsg());
        Assert.assertNull(failure2.getData());
    }
}
