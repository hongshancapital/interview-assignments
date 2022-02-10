package com.example.shortlongurl.framework.result;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class R<T> implements Serializable {
    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    private R() {
    }

    public static R ok() {
        R r = new R();
        r.success = true;
        r.code = ResultCode.SUCCESS.code;
        r.msg = ResultCode.SUCCESS.message;
        return r;
    }

    public static R ok(String msg) {
        return R.ok().msg(msg);
    }

    public static R ok(Object data) {
        return R.ok().data(data);
    }

    public static R fail() {
        R r = new R();
        r.success = false;
        r.code = ResultCode.FAIL.code;
        r.msg = ResultCode.FAIL.message;
        return r;
    }

    public static R fail(String msg) {
        return R.fail().msg(msg);
    }

    public static R fail(Object data) {
        return R.fail().data(data);
    }


    public static R result(ResultCode resultCode) {
        if (resultCode.equals(ResultCode.SUCCESS))
            return R.ok();
        return R.fail().code(resultCode.code).msg(resultCode.message);
    }

    public static R result(Integer code, String msg) {
        if (code.equals(ResultCode.SUCCESS.code))
            return R.ok();
        return R.fail().code(code).msg(msg);
    }


    public R code(Integer code) {
        this.code = code;
        return this;
    }

    public R msg(String msg) {
        this.msg = msg;
        return this;
    }

    public R success(Boolean success) {
        this.success = success;
        return this;
    }

    public R data(T data) {
        this.data = data;
        return this;
    }

}
