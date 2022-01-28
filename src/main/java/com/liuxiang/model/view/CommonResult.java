package com.liuxiang.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author liuxiang6
 * @date 2022-01-06
 **/
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {

    @NonNull
    private int code;

    @NonNull
    private String msg;

    private T data;

    public static <T> CommonResult<T> build(int code, String msg, T data) {
        return new CommonResult<T>(code, msg, data);
    }

    public static <T> CommonResult<T> success(String msg) {
        return new CommonResult<T>(0, msg, null);
    }

    public static <T> CommonResult<T> defaultError() {
        return new CommonResult<T>(1, "error", null);
    }

    public static <T> CommonResult<T> errorWithMsg(String msg) {
        return new CommonResult<T>(1, msg, null);
    }
}
