package com.scdt.china.shortdomain.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: CZ
 * @Date: 2022/1/22 13:03
 * @Description:
 */
@Data
@Slf4j
@ApiModel
public class Result<T> {

    @ApiModelProperty("是否成功")
    private boolean success;

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("信息")
    private String message;

    @ApiModelProperty("数据")
    private T data;

    private Result() {
    }

    private Result(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private Result(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> fail(String code, String message, T data) {
        return new Result<>(false, code, message, data);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(false, code, message);
    }

    public static <T> Result<T> fail(ReturnCode returnCode) {
        return new Result<>(false, returnCode.getCode(), returnCode.getMessage());
    }

    public static <T> Result<T> fail(ReturnCode returnCode, T data) {
        return new Result<>(false, returnCode.getCode(), returnCode.getMessage(), data);
    }
}