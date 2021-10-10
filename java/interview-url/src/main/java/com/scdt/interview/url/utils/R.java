package com.scdt.interview.url.utils;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: lijin
 * @date: 2021年10月10日
 */

public class R<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("业务编码：ok-成功、fail-失败")
    private String code;

    @ApiModelProperty("描述")
    private String message;

    @ApiModelProperty("业务数据")
    private T data;

    public R(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static final <T> R<T> ok(T data){
        return new R("ok", null, data);
    }

    public static final <T> R<T> fail(String msg){
        return new R("fail", msg, null);
    }

    public String getCode() {
        return code;
    }

    public R<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public R<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }
}
