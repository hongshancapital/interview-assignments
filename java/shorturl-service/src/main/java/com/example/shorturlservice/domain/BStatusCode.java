package com.example.shorturlservice.domain;

/**
 * @Description 业务code码
 * @Author xingxing.yu
 * @Date 2022/04/15 16:55
 **/
public enum BStatusCode {
    /**
     * 参数为空
     */
    PARAM_NULL(1001, "parameter is null"),
    /**
     * 参数长度超过限制
     */
    PARAM_LENGTH_LIMIT(1002, "parameter length greater than upper limit"),
    /**
     * 服务内部错误
     */
    SERVER_ERROR(500, "server error"),
    ;
    /**
     * code
     */
    private int code;
    /**
     * 描述
     */
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
