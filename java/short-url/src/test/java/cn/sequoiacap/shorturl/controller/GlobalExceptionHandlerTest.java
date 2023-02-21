package cn.sequoiacap.shorturl.controller;

import cn.sequoiacap.shorturl.exception.StoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GlobalExceptionHandlerTest {
    @Autowired
    GlobalExceptionHandler handler;

    @Test
    public void testHandleStoreException() {
        Response<?> result = handler.handleStoreException(new StoreException());
        Assertions.assertEquals(ResponseStatus.INTERNAL_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("system error, please retry", result.getMessage());
    }

    @Test
    public void testHandleUnknownException() {
        Response<?> result = handler.handleUnknownException(new Exception());
        Assertions.assertEquals(ResponseStatus.INTERNAL_ERROR.getCode(), result.getCode());
        Assertions.assertEquals("server internal error", result.getMessage());
    }
}
