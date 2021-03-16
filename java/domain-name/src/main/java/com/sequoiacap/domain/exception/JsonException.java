package com.sequoiacap.domain.exception;

/**
 * JSON解析异常
 */
public class JsonException extends RuntimeException {

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }
}
