package com.shorts.url.common;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 同一响应参数
 * </p>
 *
 * @author WangYue
 * @date 2022/3/21 18:15
 */
@Data
@Builder
public class Result<T> {
    public static final int SUCCESS = 0;
    public static final int FAIL = -1;

    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(SUCCESS)
                .data(data)
                .build();
    }

    public static <T> Result<T> fail(String msg) {
        return Result.<T>builder()
                .code(FAIL)
                .msg(msg)
                .build();
    }
}
