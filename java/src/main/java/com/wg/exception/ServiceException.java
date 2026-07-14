package com.wg.exception;

public class ServiceException extends RuntimeException {

    private Integer code;
    private String message;

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

}