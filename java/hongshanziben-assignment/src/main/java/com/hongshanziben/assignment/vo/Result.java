package com.hongshanziben.assignment.vo;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by KINGSJ.YUAN@FOXMAIL.COM on 2021-07-08.
 */
public class Result<T> implements Serializable {

    private static final String SUCCESS = "200";

    private static final String ERROR = "500";

    private static final String MSG_SUCCESS = "操作成功";

    private static final String MSG_ERROR = "操作失败";

    private T data;

    private String msg;

    private String code;

    public Result(T data, String msg, String code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    public Result(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public Result() {

    }

    public static <T> Result success(T data) {
        return new Result(data, MSG_SUCCESS, SUCCESS);
    }

    public static <T> Result error(String msg) {
        return new Result(StringUtils.isNotBlank(msg) ? msg : MSG_ERROR, ERROR);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
