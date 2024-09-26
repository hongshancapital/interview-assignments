package org.demo.shortlink.model;

import java.io.Serializable;

/**
 * @author wsq
 * @date 2022/3/26 002616:30
 * @description:
 */

public class Result implements Serializable {

    private static final long serialVersionUID = -2792556188993845048L;

    private static final int ERROR = 500;

    private static final int NORMAL = 200;

    private Integer code;

    private String message;

    private Object data;

    public Result(final Integer code, final String message, final Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public static Result success(final String data) {
        return error(NORMAL, "success", data);
    }

    public static Result error(final String msg) {
        return error(ERROR, msg);
    }

    public static Result error(final int code, final String msg) {
        return get(code, msg, null);
    }

    public static Result error(final int code, final String msg, final Object data) {
        return get(code, msg, data);
    }

    public static Result timeout(final String msg) {
        return error(ERROR, msg);
    }

    private static Result get(final int code, final String msg, final Object data) {
        return new Result(code, msg, data);
    }
}
