package com.zz.exception;

import com.zz.exception.code.SystemErrorCodeEnum;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class BusinessExceptionTest {
    @Test
    public void testConstruct() {
        BusinessException be1 = new BusinessException(SystemErrorCodeEnum.SYS_002, "122", new Exception());
        BusinessException be = new BusinessException(SystemErrorCodeEnum.SYS_002, new Exception());
        be1.setCode("123");
        be1.setMessage("456");
    }
}