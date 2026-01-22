package com.scdt.url.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Collections.unmodifiableMap;

/**
 * Base exception class for all business exceptions
 */

public abstract class AppException extends RuntimeException {
    private final ErrorCode code;
    private final Map<String, Object> data = newHashMap();

    protected AppException(ErrorCode code, Map<String, Object> data) {
        super(format(code, data));
        this.code = code;
        this.data.putAll(data);
    }

    protected AppException(ErrorCode code, Map<String, Object> data, Throwable cause) {
        super(format(code, data), cause);
        this.code = code;
        this.data.putAll(data);
    }

    private static String format(ErrorCode errorCode, Map<String, Object> data) {
        return String.format("[%s]%s:%s.", errorCode.toString(), errorCode.getMessage(), data.toString());
    }

    public ErrorCode getCode() {
        return code;
    }

    public Map<String, Object> getData() {
        return unmodifiableMap(data);
    }

    public HttpStatus httpStatus() {
        return code.getStatus();
    }

    public String userMessage() {
        return code.getMessage();
    }

}
