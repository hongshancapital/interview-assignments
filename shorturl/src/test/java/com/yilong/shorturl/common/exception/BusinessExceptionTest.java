package com.yilong.shorturl.common.exception;


import com.yilong.shorturl.common.enums.ErrorEnum;
import com.yilong.shorturl.model.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BusinessExceptionTest {

    @Test
    public void testConstruct1() {
        BusinessException businessException = new BusinessException("Error");
        Assertions.assertEquals(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode(), businessException.getCode());
    }

    @Test
    public void testConstruct2() {
        BusinessException businessException = new BusinessException(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), ErrorEnum.INVALID_ARGUMENT.getErrorMsg());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), businessException.getCode());
    }

    @Test
    public void testConstruct3() {
        BusinessException businessException = new BusinessException(ErrorEnum.INVALID_ARGUMENT);
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), businessException.getStatus());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), businessException.getCode());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorMsg(), businessException.getMessage());
    }

    @Test
    public void testConstruct4() {
        BusinessException businessException = new BusinessException(ErrorEnum.INVALID_ARGUMENT, "short url");
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), businessException.getStatus());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), businessException.getCode());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorMsg() + " short url", businessException.getMessage());

        BusinessException businessException1 = new BusinessException(ErrorEnum.INVALID_ARGUMENT, null);
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), businessException1.getStatus());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), businessException1.getCode());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorMsg(), businessException1.getMessage());
    }

    @Test
    public void testToError() {
        BusinessException businessException = new BusinessException(ErrorEnum.INVALID_ARGUMENT);
        Error error = businessException.toError();
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), error.getCode());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorMsg(), error.getMessage());
    }

    @Test
    public void testGetStatus() {
        BusinessException businessException = new BusinessException(ErrorEnum.INVALID_ARGUMENT);
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), businessException.getStatus());
    }

    @Test
    public void testGetCode() {
        BusinessException businessException = new BusinessException(ErrorEnum.INVALID_ARGUMENT);
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), businessException.getCode());
    }

    @Test
    public void testGetMessage() {
        BusinessException businessException = new BusinessException(ErrorEnum.INVALID_ARGUMENT);
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorMsg(), businessException.getMessage());
    }
}
