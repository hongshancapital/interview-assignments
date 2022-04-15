package com.example.shorturlservice.domain;

/**
 * @Description 业务code码
 * @Author xingxing.yu
 * @Date 2022/04/15 16:55
 **/
public enum BStatusCode {
    PARAM_NULL(1001, "parameter is null"),
    PARAM_LENGTH_LIMIT(1002, "parameter length greater than upper limit"),
    SERVER_ERROR(500, "server error"),
    ;
    private int code;
    private String des;

    BStatusCode(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("_").append(code).append("_").append(des);
        return sb.toString();
    }
}
