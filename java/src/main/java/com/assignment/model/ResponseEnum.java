package com.assignment.model;


/**
 * 数据信息状态枚举类
 * @author mrdiyewu@gmail.com
 * @date 2021/10/11 15:33
 */
public enum ResponseEnum {
    /**
     * 2000 表示返回成功
     */
    SUCCESS(2000,"success"),
    /**
     * 1 一般错误，返回失败，一般错误
     */
    FAILED(2001, "failed"),

    /**
     * 参数校验异常
     */
    WRONG_REQUEST_PARAMETER(1001,"wrong parameter"),
    ;

    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
