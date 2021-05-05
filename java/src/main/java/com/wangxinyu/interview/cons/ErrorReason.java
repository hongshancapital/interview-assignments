package com.wangxinyu.interview.cons;

public enum ErrorReason {
    NOT_FOUND(1, "Can not find result"),
    LURL_TOO_LONG(2, "Long URL should shorter than 200 characters"),
    SURL_TOO_LONG(3,"short URL should shorter than 8 characters"),
    LURL_TOO_SHORT(4,"Long URL should longer than 8 characters"),
    NOT_ENOUGH_MEMORY(5,"can not accept new request, not enough memory"),
    OTHER_ERROR(-1, "");
    private int code;
    private String reason;

    ErrorReason(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

