package com.scc.base.constant;

/**
 * @author renyunyi
 * @date 2022/4/24 2:56 PM
 * @description status code
 */
public enum StatusCode {

    SUCCESS(0, "success"),
    FAIL(-1, "fail");

    /**
     * status code
     */
    int code;

    /**
     * response message
     */
    String msg;

    /**
     * @param code 状态码
     * @param msg 返回结果
     */
    StatusCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
