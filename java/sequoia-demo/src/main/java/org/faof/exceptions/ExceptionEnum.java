package org.faof.exceptions;

public enum ExceptionEnum implements IApplicationException{
    SUCCESS(0, ""),
    REQUEST_BODY_INVALID(1, "request body format invalid."),
    SHORT_URL_INVALID(2, "short url format invalid"),
    LONG_URL_INVALID(3, "long url format invalid"),
    MEMORY_OVERFLOW(4, "can not store more short url."),
    RUNTIME_ERROR(500, "failed with system internal error.")
    ;

    private int errorCode;

    private String errorMessage;

    ExceptionEnum(int code, String message) {
        errorCode = code;
        errorMessage = message;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
