package com.scdt.demo.common.web;

import lombok.Getter;
import lombok.Setter;

public class CodeMessage {
    @Getter
    private String code;

    @Setter
    @Getter
    private String message;
    private CodeMessage() {

    }

    CodeMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static final CodeMessage SUCCESS = new CodeMessage("A0000", "success.");

    public static final CodeMessage INVALID_URL = new CodeMessage("E0001", "Invalid url.");

    public static final CodeMessage INVALID_SHORT_URL = new CodeMessage("E0002", "Invalid short url.");
}
