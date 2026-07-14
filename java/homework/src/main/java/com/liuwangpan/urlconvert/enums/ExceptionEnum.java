package com.liuwangpan.urlconvert.enums;

/**
 * exception enums
 *
 * @author wp_li
 */
public enum ExceptionEnum {

    INVALID_URL(10000000L, "Illegal URL,url should start with HTTP or HTTPS "),
    NOT_FOUND(10000001L, "No valid long address information was found ");


    //code
    private final Long code;

    //exception infomations
    private final String message;

    ExceptionEnum(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}