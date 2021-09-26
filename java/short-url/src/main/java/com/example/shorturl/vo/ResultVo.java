package com.example.shorturl.vo;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.UUID;

public class ResultVo<T> {
    /**
     * 自定义状态码
     */
    private int code;

    /**
     * 自定义消息
     */
    private String msg;

    /**
     * 返回数据对象或者异常对象
     */
    private T data;

    /**
     * 用于跟踪的唯一uuid
     */
    private String uuid;

    public static <T> ResultVo<T> success(T data) {
        ResultVo<T> resultVo = new ResultVo<>(ResultCode.SUCCESS, data);
        return resultVo;
    }

    public static <T> ResultVo<T> failure(int code, String msg, T data) {
        Assert.isTrue(ResultCode.SUCCESS.getCode() != code, "code状态码必须是错误码");
        ResultVo<T> resultVo = new ResultVo<T>(code, msg, data);
        return resultVo;
    }

    public static <T> ResultVo<T> failure(int code, String msg) {
        return failure(code, msg, null);
    }

    public static <T> ResultVo<T> failure(ResultCode resultCode, T data) {
        return failure(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public static <T> ResultVo<T> failure(HttpStatus httpStatus, T data) {
        Assert.notNull(httpStatus, "HttpStatus can't be null");
        return failure(httpStatus.value(), httpStatus.getReasonPhrase(), data);
    }

    public ResultVo() {

    }

    public ResultVo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.uuid = UUID.randomUUID().toString();
    }

    public ResultVo(StatusCode statusCode, T data) {
        this(statusCode.getCode(), statusCode.getMsg(), data);
    }

    public ResultVo(StatusCode statusCode) {
        this(statusCode, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultVo<?> resultVo = (ResultVo<?>) o;
        return code == resultVo.code && Objects.equals(msg, resultVo.msg) && Objects.equals(data, resultVo.data) && Objects.equals(uuid, resultVo.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, data, uuid);
    }
}
