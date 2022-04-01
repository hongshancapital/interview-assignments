package com.zz.util;

import com.zz.exception.BusinessException;
import com.zz.param.Response;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class ResponseUtilTest {

    @Test
    public void buildSuccess() {
        Response response = ResponseUtil.buildSuccess();
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testBuildSuccess() {
        Response response = ResponseUtil.buildSuccess("test");
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void businessError() {
        Response response = ResponseUtil.businessError(new BusinessException("test", "test"));
        Assert.assertFalse(response.isSuccess());
    }

    @Test
    public void businessDefault() {
        Response response = ResponseUtil.businessDefault();
        Assert.assertFalse(response.isSuccess());
    }
}