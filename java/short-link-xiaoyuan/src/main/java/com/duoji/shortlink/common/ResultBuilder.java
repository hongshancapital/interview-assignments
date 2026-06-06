package com.duoji.shortlink.common;

import java.util.List;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月19日 12:57:00
 */
public class ResultBuilder {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public static <T> Result<T> buildSuccess(T data) {
        return new Result(true, "success", "success", data);
    }

    public static <T> Result<List<T>> buildSuccessList(List<T> data) {
        return new Result(true, "success", "success", data);
    }

    public static <T> Result<T> buildSuccess() {
        return buildSuccess(null);
    }

    public static <T> Result<T> buildFailed(String message) {
        return new Result(false, "fail", message, (Object)null);
    }

    public static <T> Result<T> buildFailed(String code, String message) {
        return new Result(false, code, message, (Object)null);
    }
}
