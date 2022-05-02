package com.mortimer.shortenurl.enums;

import lombok.Getter;

@Getter
public enum ResponseStatusEnum {
    SUCCESS(200, "success"),
    FAILED(4000, "failed"),
    FAIELD_NOT_EXIST_SHORTURL(4010, "failed: shorturl does not exist"),
    ERROR(5000, "error");

    private int code;
    private String message;

    ResponseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
