package com.scdt.shortlink.entity;

import com.scdt.shortlink.exception.ErrorEnum;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    public final static String SUCCESS_CODE = "000000";

    // 返回错误码
    private String code;

    // 返回错误信息
    private String msg;

    // 返回数据
    private T data;

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .code(SUCCESS_CODE)
                .data(data)
                .build();
    }

    public static CommonResponse fail(ErrorEnum error) {
        return CommonResponse.builder()
                .code(error.getCode())
                .msg(error.getMsg())
                .build();
    }
}
