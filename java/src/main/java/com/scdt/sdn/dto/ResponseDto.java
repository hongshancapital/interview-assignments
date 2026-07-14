package com.scdt.sdn.dto;

import java.io.Serializable;

import com.scdt.sdn.constants.SdnConstants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "请求返回体", description = "请求返回体")
public class ResponseDto<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请求返回code")
    protected int code;
    @ApiModelProperty(value = "请求返回msg")
    protected String message;
    @ApiModelProperty(value = "请求返回数据")
    protected T data;

    public ResponseDto() {
        this.code = SdnConstants.CODE200;
        this.message = SdnConstants.MSGOK;
    }

    public ResponseDto(T data) {
        this.code = SdnConstants.CODE200;
        this.message = SdnConstants.MSGOK;
        this.data = data;
    }

    public ResponseDto(int code, String message) {
        this(code, message, null);
    }

    public ResponseDto(int code, String message, T data) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseDto<T> instanceOf() {
        return new ResponseDto();
    }

    public static <T> ResponseDto<T> instanceOf(T data) {
        return new ResponseDto(data);
    }

    public static <T> ResponseDto<T> error(int code, String msg) {
        return new ResponseDto(code, msg);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
