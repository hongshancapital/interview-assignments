package com.domain.exception;

/**
 * @author ：ji
 * @description：异常枚举类
 */
public enum ExceptionEnums {

    PARAM_URL_ERROR(4000,"URL地址格式不正确!"),
    PARAM_NULL(4001,"参数不能为空!"),
    // 非法短链接
    PARAM_SHORT_URL_ERROR(4002,"非法链接!"),



    FAIL(9999,"网络请求异常，请稍后重试!");

    private int code;
    private String message;
    ExceptionEnums(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
