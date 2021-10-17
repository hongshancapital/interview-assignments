package com.zhangzheng.homework.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhangzheng.homework.exception.ErrorType;
import com.zhangzheng.homework.exception.SystemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/9 下午3:20
 */
@ApiModel(
        description = "rest请求的返回模型，所有rest接口正常都返回该类的对象"
)
public class WebResponse<T> {
    @ApiModelProperty(
            value = "处理结果code",
            required = true
    )
    private String code;
    @ApiModelProperty("处理结果描述信息")
    private String message;
    @ApiModelProperty("请求结果生成时间戳")
    private long timestamp;
    @ApiModelProperty("处理结果数据信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public WebResponse(ErrorType errorType) {
        this.timestamp = System.currentTimeMillis();
        this.code = errorType.getResult() + "";
        this.message = errorType.getMessage();
    }

    public WebResponse(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    private WebResponse(String code, String message, T data) {
        this.timestamp = System.currentTimeMillis();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> WebResponse<T> success() {
        return success(null);
    }

    public static <T> WebResponse<T> success(T data) {
        return new WebResponse(SystemType.SUCCESS, data);
    }

    public static <T> WebResponse<T> fail() {
        return fail((ErrorType)SystemType.ERROR);
    }

    public static <T> WebResponse fail(ErrorType errorType) {
        return fail((ErrorType)errorType, (Object)null);
    }

    public static <T> WebResponse<T> fail(ErrorType errorType, T data) {
        return new WebResponse(errorType, data);
    }

    public static <T> WebResponse<T> fail(String message) {
        return fail(String.valueOf(SystemType.ERROR.getResult()), message);
    }

    public static <T> WebResponse<T> fail(String code, String message) {
        return new WebResponse(code, message, (Object)null);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return String.valueOf(SystemType.SUCCESS.getResult()).equals(this.code);
    }

    @JsonIgnore
    public boolean isFail() {
        return !this.isSuccess();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public WebResponse() {
        this.timestamp = System.currentTimeMillis();
    }
}

