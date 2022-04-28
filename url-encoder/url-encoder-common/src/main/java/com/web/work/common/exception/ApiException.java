package com.web.work.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 8:42 PM
 */
public class ApiException extends RuntimeException {

    public ApiException(HttpStatus status, ErrorCode errorCode, String message) {
        super(message);
    }
}
