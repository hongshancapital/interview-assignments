package com.example.shorturl.vo;

public enum ResultCode implements StatusCode {

    SUCCESS(1000, "请求成功"),
    FAILURE(1001, "请求失败"),
    VALIDATION_ERROR(1002, "参数校验错误"),
    RESPONSE_WRAPPER_ERROR(1003, "response返回包装错误"),
    BAD_REQUEST(1400, "请求参数不正确"),
    UNAUTHORIZED(1401, "账号未登录"),
    FORBIDDEN(1403, "没有该操作权限"),
    NOT_FOUND(1404, "请求未找到"),
    METHOD_NOT_ALLOWED(1405, "请求方法不正确"),
    INTERNAL_SERVER_ERROR(1500, "内部服务器错误");


    /**
     * 自定义状态码
     */
    private final int code;
    /**
     * 自定义消息
     */
    private final String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
