package com.web.work.common.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * http response wrapper object
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 8:42 PM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> implements Serializable {

    private T data;
    private boolean success;
    private String errorCode;
    private String message;

    private ResponseWrapper() {
    }

    public static <T> ResponseWrapper<T> success(T data) {
        if (data == null) {
            return ResponseWrapper.success();
        }
        if (!(data instanceof Serializable)) {
            throw new RuntimeException(String.format("class %s must implements java.io.Serializable", data.getClass().getName()));
        }
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.success = true;
        responseWrapper.message = "OK";
        responseWrapper.data = data;
        return responseWrapper;
    }

    public static <T> ResponseWrapper<T> success() {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.success = true;
        responseWrapper.message = "OK";
        responseWrapper.data = null;
        return responseWrapper;
    }

    public static <T> ResponseWrapper<T> fail(ErrorCode errorCode) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.success = false;
        responseWrapper.errorCode = errorCode.getCode();
        responseWrapper.message = errorCode.getMessage();
        return responseWrapper;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

}
