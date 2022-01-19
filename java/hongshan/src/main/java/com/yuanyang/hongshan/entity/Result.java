package com.yuanyang.hongshan.entity;

import java.io.Serializable;

/**
 * 统一结果集
 *
 * @author yuanyang
 * @date 2021/12/16 5:00 下午
 * 类Result.java的实现描述：返回结果.
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -2333223347745982906L;

    /**
     * 结果,即相应业务数据.
     */
    private T value;
    /**
     * 结果状态码.
     */
    private ResultCode rc = ResultCode.SUCCESS;
    /**
     * 补充信息.
     */
    private String     msg;

    public Result() {
    }

    public Result(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    /**
     * 是否成功.
     */
    public boolean isSuccess() {
        return ResultCode.SUCCESS.equals(rc);
    }

    public ResultCode getRc() {
        return rc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setRc(ResultCode rc) {
        this.rc = rc;
    }

    public String getRcAndMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append(rc).append("-").append(msg);
        return sb.toString();
    }
    public static <T> Result<T> newInstance() {
        return new Result<T>();
    }

    public static <T> Result<T> newErrorResult(ResultCode rc, String msg) {
        Result result = newInstance();
        result.setRc(rc);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> newErrorResult(ResultCode rc) {
        Result result = newInstance();
        result.setRc(rc);
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "value=" + value +
                ", rc=" + rc +
                ", msg='" + msg + '\'' +
                '}';
    }
}