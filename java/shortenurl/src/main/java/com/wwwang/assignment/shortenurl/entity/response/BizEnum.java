package com.wwwang.assignment.shortenurl.entity.response;

public enum BizEnum {

    SUCCESS(10000, "SUCCESS"),
    OPERATION_FAILED(10001, "OPERATION_FAILED");

    private int code;
    private String name;

    private BizEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

}
