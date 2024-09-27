package com.sequoia.shorturl.common;

import io.swagger.annotations.ApiModel;
import org.springframework.http.HttpStatus;

/**
 * @Author: xxx
 * @Description: web统一返回对象
 * @Date: 2022/1/2 23:31
 * @Version: 1.0.0
 */
@ApiModel(value = "web统一返回对象", description = "web统一返回对象")
public class ApiResult<T> {
    private final Integer code;
    private final String msg;
    private final T data;

    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.ok("success", data);
    }

    public static <T> ApiResult<T> ok(String msg, T data) {
        return new ApiResult<>(HttpStatus.OK.value(), msg, data);
    }

    public static <T> ApiResult<T> failure(String msg) {
        return new ApiResult<T>(HttpStatus.BAD_REQUEST.value(), msg, null);
    }

    public static <T> ApiResult<T> create(Integer code, String msg, T data) {
        return new ApiResult<>(code, msg, data);
    }

    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
