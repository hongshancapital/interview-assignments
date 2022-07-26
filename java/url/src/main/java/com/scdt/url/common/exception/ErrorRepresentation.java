package com.scdt.url.common.exception;

import com.scdt.url.common.logging.RequestIdAwareRepresentation;
import org.springframework.http.HttpStatus;

public class ErrorRepresentation extends RequestIdAwareRepresentation {
    private final ErrorDetail error;

    private ErrorRepresentation(ErrorDetail error) {
        this.error = error;
    }

    public static ErrorRepresentation from(ErrorDetail error) {
        return new ErrorRepresentation(error);
    }

    public ErrorDetail getError() {
        return error;
    }

    public HttpStatus httpStatus() {
        return error.httpStatus();
    }
}
