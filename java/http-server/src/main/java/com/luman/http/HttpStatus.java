package com.luman.http;

public enum HttpStatus {
    OK("200 OK"),
    NOT_FOUND("404 Not Found"),
    UNAUTHORIZED("401 Unauthorized"),
            ;

    private String status;
    HttpStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
