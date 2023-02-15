package com.yilong.shorturl.model;

import com.yilong.shorturl.common.enums.ErrorEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorTest {

    @Test
    public void testConstruct() {
        new Error();
    }

    @Test
    public void testGetCode() {
        new Error().getCode();
    }

    @Test
    public void testSetCode() {
        new Error().setCode(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode());
    }

    @Test
    public void testGetAndSetMessage() {
        Error error = new Error();
        error.setCode(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode());
        error.setMessage(ErrorEnum.UNHANDLED_EXCEPTION.getErrorMsg());
        Assertions.assertEquals(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode(), error.getCode());
        Assertions.assertEquals(ErrorEnum.UNHANDLED_EXCEPTION.getErrorMsg(), error.getMessage());
    }

    @Test
    public void testToString() {
        new Error().toString();
    }

    @Test
    public void testHashCode() {
        new Error().hashCode();
    }

    @Test
    public void testEquals() {
        Error a = new Error();
        a.setCode(ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode());
        Error b = new Error();
        Assertions.assertFalse(a.equals(b));
    }
}
