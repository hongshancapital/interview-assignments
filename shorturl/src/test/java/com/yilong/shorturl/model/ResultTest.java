package com.yilong.shorturl.model;

import com.yilong.shorturl.common.enums.ErrorEnum;
import com.yilong.shorturl.common.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultTest {

    @Test
    public void testConstruct() {
        Result result = new Result();
        Assertions.assertEquals(Result.STATUS_SUCCESS, result.getStatus());
        Assertions.assertEquals(true, result.getSuccess());
        Assertions.assertEquals(null, result.getData());
        Assertions.assertEquals(null, result.getError());
    }

    @Test
    public void testBuilder0(){
        Result.Builder builder = new Result.Builder();
    }

    @Test
    public void testBuilder1(){
        Result result = Result.Builder.success();
        Assertions.assertEquals(Result.STATUS_SUCCESS, result.getStatus());
        Assertions.assertEquals(true, result.getSuccess());
        Assertions.assertEquals(null, result.getData());
        Assertions.assertEquals(null, result.getError());
    }

    @Test
    public void testBuilder2(){
        Result result = Result.Builder.success("success");
        Assertions.assertEquals(Result.STATUS_SUCCESS, result.getStatus());
        Assertions.assertEquals(true, result.getSuccess());
        Assertions.assertEquals("success", result.getData());
        Assertions.assertEquals(null, result.getError());
    }

    @Test
    public void testBuilder3(){
        Result result = Result.Builder.error(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode(), ErrorEnum.UNHANDLED_EXCEPTION.getErrorMsg());
        Assertions.assertEquals(Result.STATUS_ERROR, result.getStatus());
        Assertions.assertEquals(false, result.getSuccess());
        Assertions.assertEquals(null, result.getData());
        Assertions.assertEquals(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode(), result.getError().getCode());
        Assertions.assertEquals(ErrorEnum.UNHANDLED_EXCEPTION.getErrorMsg(), result.getError().getMessage());
    }

    @Test
    public void testBuilder4(){
        Result result = Result.Builder.error(400, ErrorEnum.INVALID_ARGUMENT.getErrorCode(), ErrorEnum.INVALID_ARGUMENT.getErrorMsg());
        Assertions.assertEquals(400, result.getStatus());
        Assertions.assertEquals(false, result.getSuccess());
        Assertions.assertEquals(null, result.getData());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), result.getError().getCode());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorMsg(), result.getError().getMessage());
    }

    @Test
    public void testBuilder5(){
        BusinessException businessException = new BusinessException(ErrorEnum.INVALID_ARGUMENT);
        Result result = Result.Builder.error(businessException);
        Assertions.assertEquals(false, result.getSuccess());
        Assertions.assertEquals(null, result.getData());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getHttpStatus(), result.getStatus());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorCode(), result.getError().getCode());
        Assertions.assertEquals(ErrorEnum.INVALID_ARGUMENT.getErrorMsg(), result.getError().getMessage());
    }

    @Test
    public void testToString() {
        new Result().toString();
    }

    @Test
    public void testHashCode() {
        new Result().hashCode();
    }

    @Test
    public void testEquals() {
        Result a = new Result();
        a.setData("Result");
        a.setStatus(200);
        a.setSuccess(true);
        a.setError(null);
        Assertions.assertEquals("Result", a.getData());
        Assertions.assertEquals(200, a.getStatus());
        Assertions.assertEquals(true, a.getSuccess());
        Assertions.assertEquals(null, a.getError());
        Result b = new Result();
        Assertions.assertFalse(a.equals(b));
    }
}
