package com.test.shorturl.common.result;


import com.test.shorturl.common.exception.ShortUrlException;

import java.io.Serializable;

/**
 * @Author: liurenyuan
 * @Date: 2021/11/10
 * @Version: 1.0
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -3948389268046368059L;
    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String SUCCESS = "SUCCESS";
    private static final String EMPTY = "";

    private String code;

    private String msg;

    private T data;


    Result() {
    }

    public static <T> Result<T> success(T data) {
        Result result = new Result<T>();
        result.code = ZERO;
        result.msg = SUCCESS;
        result.data = data;
        return result;
    }

    public static <T> Result<T> failure(ResultCodeEnum resultCodeEnum) {
        Result result = new Result();
        result.setResultCode(resultCodeEnum);
        return result;
    }
    public static <T> Result<T> failure(ShortUrlException shortUrlException) {
        Result result = new Result();
        result.setResultCode(shortUrlException);
        result.setData(EMPTY);
        return result;
    }

    public static <T> Result<T> failure(String failMsg) {
        Result result = new Result();
        result.setMsg(failMsg);
        result.setCode(ONE);
        return result;
    }


    public void setResultCode(ResultCodeEnum code) {
        this.code = code.code();
        this.msg = code.message();
    }
    public void setResultCode(ShortUrlException shortUrlException) {
        this.code = shortUrlException.getErrorCode();
        this.msg = shortUrlException.getMessage();
    }

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }


    public T getData() {
        return data;
    }


    private void setData(T data) {
        this.data = data;
    }
}

