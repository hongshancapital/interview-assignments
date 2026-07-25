package com.bolord.shorturl.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    void success() {
        Result<String> result = new Result<>(true, "test");

        assertTrue(result.isSuccess());
        assertEquals(result.getData(), "test");

        Result<String> result2 = new Result<>();
        result2.setSuccess(true);
        result2.setData("test");
        result2.setCode(null);
        result2.setMessage(null);

        assertTrue(result2.isSuccess());
        assertEquals(result2.getData(), "test");
        assertEquals(result.toString(), result2.toString());
        assertEquals(result.hashCode(), result2.hashCode());
        assertEquals(result2, result);

        result = new Result<>(true);
        assertTrue(result.isSuccess());
        assertNotEquals(result, null);
        assertNotEquals(result, "");

        result = new Result<>(true, "test", null);
        assertTrue(result.isSuccess());
        assertEquals(result.getData(), "test");
    }

    @Test
    void fail() {
        Result<Void> result = new Result<>(false, 500, "error");

        assertFalse(result.isSuccess());
        assertEquals(result.getCode(), 500);
        assertEquals(result.getMessage(), "error");
    }

}
