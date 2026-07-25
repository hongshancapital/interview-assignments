package com.bolord.shorturl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ShortUrlExceptionTest {

    @Test
    void throwEx() {
        assertThrows(ShortUrlException.class, () -> {
            throw new ShortUrlException(999, "存储已达到上限");
        });
    }

    @Test
    void constructorAndGetSet() {
        ShortUrlException ex = new ShortUrlException(999, "存储已达到上限");
        assertEquals(999, ex.getCode());
        assertEquals("存储已达到上限", ex.getMessage());

        ex.setCode(888);
        ex.setMessage("test");
        assertEquals(888, ex.getCode());
        assertEquals("test", ex.getMessage());
    }

}
