package com.scdt.domain.common;

import lombok.Data;

import java.util.Map;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@Data
public class R<T> {

    public final static int SUCCESS_STATUS = 1;
    public final static int DEFAULT_ERROR_STATUS = 0;

    /**
     * 1 成功
     * 其他非1 则标记失败
     */
    public int status;
    public String errorMsg;
    /**
     * 可以定义一些公共属性
     */
    public Map<String, Object> resultParam;

    public T data;

    public R(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public R(String errorMsg, int status, T data) {
        this.status = status;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static R success(Object data) {
        return new R(SUCCESS_STATUS, data);
    }

    public static R error(String errorMsg, int status, Object data) {
        return new R(errorMsg, status, data);
    }

    public static R error(String errorMsg) {
        return new R(errorMsg, DEFAULT_ERROR_STATUS, null);
    }

    public static R error(String errorMsg, Object data) {
        return new R(errorMsg, DEFAULT_ERROR_STATUS, data);
    }

    public boolean isSuccess() {
        return this.status == SUCCESS_STATUS;
    }
}
