package org.example.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ApiResult<T> {
    public static final String INVALID_URL_CODE = "ERROR.001";
    public static final String CONVERT_URL_FAILED_CODE = "ERROR.002";

    @ApiModelProperty("错误码")
    private String errorCode;

    @ApiModelProperty("错误说明")
    private String message;

    @ApiModelProperty(value = "响应结果，有错误时为null", dataType = "泛型")
    private T data;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
