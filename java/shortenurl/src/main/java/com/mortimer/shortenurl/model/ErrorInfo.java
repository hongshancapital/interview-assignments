package com.mortimer.shortenurl.model;

import lombok.Getter;

@Getter
public class ErrorInfo {
    private String fieldName;
    private String errorMsg;

    public ErrorInfo(String fieldName, String errorMsg) {
        this.fieldName = fieldName;
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "fieldName='" + fieldName + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
