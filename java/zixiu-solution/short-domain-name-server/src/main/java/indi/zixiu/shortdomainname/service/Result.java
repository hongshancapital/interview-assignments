package indi.zixiu.shortdomainname.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Service 类的方法返回值类型中，带有业务执行状态的返回值类型的基类
 * @param <T> 业务执行成功时的业务结果数据的类型
 */
public class Result<T> {
    public static final int CODE_SUCCESS = 0;
    public static final String MESSAGE_SUCCESS = "成功";

    private int code;
    private String message;
    private T data;

    public static <T> Result<T> buildSuccessResult(T data) {
        return new Result<>(CODE_SUCCESS, MESSAGE_SUCCESS, data);
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
