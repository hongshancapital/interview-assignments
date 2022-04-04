package com.liupf.tiny.url.common;

import com.liupf.tiny.url.enums.ApiCodeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;


/**
 * 响应信息主体
 */
@Getter
@ToString
@ApiModel(value = "通用返回结果")
public class ApiResult<T> {

    @ApiModelProperty(value = "响应码")
    private Integer code;

    @ApiModelProperty(value = "描述信息")
    private String msg;

    @ApiModelProperty(value = "数据体")
    private T result;

    public static <T> ApiResult<T> ok(T result) {
        return buildResult(result, ApiCodeEnum.SUCCESS, ApiCodeEnum.SUCCESS.getMsg());
    }

    public static <T> ApiResult<T> ok(String message, T result) {
        return buildResult(result, ApiCodeEnum.SUCCESS, message);
    }

    public static <T> ApiResult<T> failed(ApiCodeEnum resultEnum) {
        return buildResult(null, resultEnum, resultEnum.getMsg());
    }

    public static <T> ApiResult<T> failed(String message) {
        return buildResult(null, ApiCodeEnum.SERVICE_BUSY, message);
    }

    private static <T> ApiResult<T> buildResult(T result, ApiCodeEnum resultEnum, String message) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.code = resultEnum.getCode();
        apiResult.result = result;
        apiResult.msg = message;
        return apiResult;
    }

}

