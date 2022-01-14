package com.scdt.shorturl.exception;

public enum ExceptionMessage {

    NOT_NULL("网址必填"),
    INCORRECT_URL("网址[%s]格式有误"),
    URL_LENGTH_INVALID("网址[%s]太长"),
    SHORT_URL_LENGTH_INVALID("短域名[%s]长度不符合规则"),
    SHORT_URL_EXPIRED("短域名[%s]已过期"),
    ;

    private final String msg;

    ExceptionMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
