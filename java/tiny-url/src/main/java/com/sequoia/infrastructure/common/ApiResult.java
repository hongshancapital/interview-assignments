package com.sequoia.infrastructure.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * ApiResult
 *
 * @author KVLT
 * @date 2022-03-30.
 */
@Getter
@ToString
@ApiModel(value = "通用返回结果")
public class ApiResult<T> {

    @ApiModelProperty(value = "响应码")
    private Integer code;

    @ApiModelProperty(value = "响应信息")
    private String msg;

    @ApiModelProperty(value = "响应数据体")
    private T data;

    public static <T> ApiResult<T> ok(T data) {
        return resultBuild(data, StatusCodeEnum.OK, StatusCodeEnum.OK.getMsg());
    }

    public static <T> ApiResult<T> ok(T data, String message) {
        return resultBuild(data, StatusCodeEnum.OK, message);
    }

    public static <T> ApiResult<T> error(StatusCodeEnum codeEnum) {
        return resultBuild(null, codeEnum, codeEnum.getMsg());
    }

    public static <T> ApiResult<T> error(String message) {
        return resultBuild(null, StatusCodeEnum.SERVE_ERROR, message);
    }

    public static <T> ApiResult<T> error(StatusCodeEnum codeEnum, String message) {
        return resultBuild(null, codeEnum, message);
    }

    private static <T> ApiResult<T> resultBuild(T data, StatusCodeEnum codeEnum, String message) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.code = codeEnum.getCode();
        apiResult.data = data;
        apiResult.msg = message;
        return apiResult;
    }

}
