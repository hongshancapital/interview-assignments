package com.yilong.shorturl.common.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorEnumTest {

    @Test
    public void testGetHttpStatus() {
        Assertions.assertEquals(500, ErrorEnum.UNHANDLED_EXCEPTION.getHttpStatus());
    }

    @Test
    public void testGetErrorCode() {
        Assertions.assertEquals(900500, ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode());
    }

    @Test
    public void testGetErrorMsg() {
        Assertions.assertEquals("Unhandled exception", ErrorEnum.UNHANDLED_EXCEPTION.getErrorMsg());
    }
}
