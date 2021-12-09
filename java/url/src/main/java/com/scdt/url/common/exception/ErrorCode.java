package com.scdt.url.common.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


public enum ErrorCode {

    TINY_URL_NOT_FOUND(NOT_FOUND, "没有找到短链接"),
    SYSTEM_ERROR(INTERNAL_SERVER_ERROR, "系统错误"),
    REQUEST_VALIDATION_FAILED(BAD_REQUEST, "请求数据格式验证失败");
    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    }
