package com.scdt.exception;

import com.scdt.domin.ErrorCode;
import com.scdt.domin.po.ShortLinkInfo;
import org.junit.Assert;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

/**
 * BusinessExceptionTest
 *
 * @author weixiao
 * @date 2022-04-26 19:07
 */
public class BusinessExceptionTest {

    @Test
    public void testConstruct() {
        new BusinessException(ErrorCode.SERVER_ERROR);
        new BusinessException(ErrorCode.SERVER_ERROR, "缓存故障");
    }

    @Test
    public void testGetter() {
        BusinessException exception = new BusinessException(ErrorCode.SERVER_ERROR, "缓存故障");
        Assert.assertEquals(ErrorCode.SERVER_ERROR, exception.getError());
        Assert.assertEquals("缓存故障", exception.getMsg());
    }
}
