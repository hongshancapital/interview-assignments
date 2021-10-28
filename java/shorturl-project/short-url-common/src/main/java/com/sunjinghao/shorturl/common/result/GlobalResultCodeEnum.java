package com.sunjinghao.shorturl.common.result;


/**
 *  通用返回状态码枚举
 *
 * @author: sunjinghao
 */
public enum GlobalResultCodeEnum {
    REQUEST_OK(200, "请求成功"),
    SERVER_ERROR(500,"服务异常"),
    WARN_REQUEST(10005, "软校验返回"),
    QUERY_ERROR(10003,"查询错误"),
    INVALID_REQUEST(10002, "非法的请求参数"),
    DATA_ERROR(999, "请求失败"),
    ;

    private Integer code;
    private String msg;

    GlobalResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}