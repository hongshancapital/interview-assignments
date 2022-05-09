package com.domain.urlshortener.exception;

import com.domain.urlshortener.enums.BizStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 3:33
 */
public class BizExceptionTest {

    @Test
    public void test_constructor_1() {
        BizException bizException = new BizException(BizStatus.FAILED, null);
        Assertions.assertEquals(BizStatus.FAILED, bizException.getBizStatus());
    }

    @Test
    public void test_constructor_2() {
        BizException bizException = new BizException("A", null);
        Assertions.assertEquals(BizStatus.FAILED, bizException.getBizStatus());
    }

    @Test
    public void test_constructor_3() {
        BizException bizException = new BizException(BizStatus.FAILED);
        Assertions.assertEquals(BizStatus.FAILED, bizException.getBizStatus());
    }

    @Test
    public void test_constructor_4() {
        BizException bizException = new BizException("A");
        Assertions.assertEquals(BizStatus.FAILED, bizException.getBizStatus());
    }

}
