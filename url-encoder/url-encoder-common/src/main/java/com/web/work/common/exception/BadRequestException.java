package com.web.work.common.exception;

import org.springframework.http.HttpStatus;

/**
 * custom class of bad request exception
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 8:42 PM
 */
public class BadRequestException extends ApiException {

    public BadRequestException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode, errorCode.getMessage());
    }

}
