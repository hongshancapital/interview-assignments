package com.mortimer.shortenurl.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorInfoTest {
    @Test
    public void test_constructor() {
        String fieldName = "name";
        String errorMsg = "empty";
        ErrorInfo errorInfo = new ErrorInfo(fieldName, errorMsg);
        Assertions.assertEquals(fieldName, errorInfo.getFieldName());
        Assertions.assertEquals(errorMsg, errorInfo.getErrorMsg());
        Assertions.assertNotNull(errorInfo.toString());
    }
}
