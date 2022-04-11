package com.scdt.domain.api.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tuxiaozhou
 * @date 2022/4/2
 */
@Data
public class Response<T> implements Serializable {

    public static final String SUCCESS = "200";

    public static final String ERROR = "500";

    private String code;

    private T data;

    private String message;

    private Response() {
    }

    private Response(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> Response<T> success() {
        return success(null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(SUCCESS, data, null);
    }

    public static <T> Response<T> error() {
        return error(ERROR);
    }

    public static <T> Response<T> error(String message) {
        return error(ERROR, message);
    }

    public static <T> Response<T> error(String code, String message) {
        return new Response<>(code, null, message);
    }

}
