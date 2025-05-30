package com.example.app.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
* 标准返回体
*
* @author voidm
* @date 2021/9/18
*/
@ApiModel("标准返回体")
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 19695416399404825L;
    @ApiModelProperty("状态码")
    private String code;
    @ApiModelProperty("状态消息")
    private String message;
    @ApiModelProperty("内容")
    private T result;

    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ResponseResult(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ResponseResult(ResponseCode code, String msg) {
        this.code = code.getCode();
        this.message = msg;
    }

    public ResponseResult(ResponseCode code, String msg, T result) {
        this.code = code.getCode();
        this.message = msg;
        this.result = result;
    }

    public ResponseResult(ResponseCode code, T result) {
        this(code);
        this.result = result;
    }

    public static <T> ResponseResult success() {
        return new ResponseResult(ResponseCode.SUCCESS, new Object());
    }

    public static <T> ResponseResult success(T result) {
        return null == result ? new ResponseResult(ResponseCode.SUCCESS, new Object()) :
                new ResponseResult (ResponseCode.SUCCESS, result);
    }

    public static <T> ResponseResult<T> success(String message) {
        return new ResponseResult(ResponseCode.SUCCESS, message, new Object());
    }

    public static <T> ResponseResult<T> error() {
        return new ResponseResult(ResponseCode.ERROR);
    }

    public static <T> ResponseResult<T> error(String message) {
        return new ResponseResult(ResponseCode.ERROR, message);
    }

    public static <T> ResponseResult<T> build(ResponseCode code) {
        return new ResponseResult(code);
    }

    public static <T> ResponseResult<T> build(ResponseCode code, T result) {
        return new ResponseResult(code, result);
    }

    public static <T> ResponseResult<T> build(String code, String message) {
        return new ResponseResult(code, message);
    }

    public static <T> ResponseResult<T> build(ResponseCode code, String error) {
        return new ResponseResult(code, error);
    }

    public ResponseResult<T> withResult(T value) {
        this.setResult(value);
        return this;
    }

    @Override
    public String toString() {
        return "ResponseResult(code=" + this.getCode() + ", message=" + this.getMessage() + ", result=" + this.getResult() + ")";
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setResult(final T result) {
        this.result = result;
    }

    public T getResult() {
        return this.result;
    }
}