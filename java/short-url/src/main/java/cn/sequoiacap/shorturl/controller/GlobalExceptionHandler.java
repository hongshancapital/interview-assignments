package cn.sequoiacap.shorturl.controller;

import cn.sequoiacap.shorturl.exception.StoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(StoreException.class)
    public Response<?> handleStoreException(StoreException exception) {
        LOGGER.error("write store failed", exception);
        return Response.fail(ResponseStatus.INTERNAL_ERROR, "system error, please retry");
    }

    @ExceptionHandler(Exception.class)
    public Response<?> handleUnknownException(Exception exception) {
        LOGGER.error("unknown error", exception);
        return Response.fail(ResponseStatus.INTERNAL_ERROR);
    }
}
