package com.zc.shorturl.snowflake.exception;

import org.junit.jupiter.api.Test;

public class CheckLastTimeExceptionTest {
    @Test
    public void testCheckLastTimeException() {
        CheckLastTimeException checkLastTimeException = new CheckLastTimeException("test checkLastTimeException");
    }
}
