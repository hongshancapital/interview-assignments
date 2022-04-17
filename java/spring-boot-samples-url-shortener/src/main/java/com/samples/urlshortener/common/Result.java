package com.samples.urlshortener.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liuqu
 * 通用response
 */
@ApiModel(value = "响应")
public class Result<T> {

    @ApiModelProperty(value = "状态码", required = true, example = "1", position = 1)
    private int code;

    @ApiModelProperty(value = "返回描述信息", required = true, example = "success", position = 2)
    private String message;

    @ApiModelProperty(value = "响应数据", position = 3)
    private T data;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ErrorCodeEnum rc, T data) {
        this.code = rc.getCode();
        this.message = rc.getValue();
        this.data = data;
    }

    public Result() {
        this.code = ErrorCodeEnum.SUCCESS.getCode();
        this.message = ErrorCodeEnum.SUCCESS.getValue();
    }

    public static Result<Void> ok() {
        return new Result<>(ErrorCodeEnum.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ErrorCodeEnum.SUCCESS, data);
    }

    public static Result<Void> fail() {
        return new Result<>(ErrorCodeEnum.FAIL, null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(ErrorCodeEnum.FAIL, data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
