package com.wenchao.jacoco.demo.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 通用结果信息
 * @author Wenchao Gong
 * @date 2021/12/15 16:17
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "通用结果信息")
public class Ret<T> {
    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 500;
    @ApiModelProperty("数据")
    private T data;

    @ApiModelProperty("业务编码")
    private int code;

    @ApiModelProperty("编码对应说明")
    private String msg;

    public static <T> Ret<T> ok(T data) {
        return new Ret<>(data, SUCCESS_CODE, "OK");
    }

    public static <T> Ret<T> error(String msg) {
        return new Ret<>(null, ERROR_CODE, msg);
    }

    public static <T> Ret<T> error(int code, String msg) {
        return new Ret<>(null, code, msg);
    }
}
