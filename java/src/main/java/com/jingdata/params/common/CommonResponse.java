package com.jingdata.params.common;

import com.jingdata.exception.BizException;
import com.jingdata.exception.ExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公共响应参数
 *
 * @Author wuyl
 * @Date 2022/4/6 23:01:13
 */
@Data
@ApiModel
public class CommonResponse<T> {

    @ApiModelProperty(value = "响应码", required = true)
    private Integer code;

    @ApiModelProperty(value = "响应描述", required = true)
    private String message;

    @ApiModelProperty(value = "响应数据", required = false)
    private T data;

    public CommonResponse(BizException e) {
        this.code = e.getCode();
        this.message = e.getErrorMessage();
    }

    public CommonResponse(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getErrorMessage();
    }

    public CommonResponse(ExceptionCode exceptionCode, T data) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getErrorMessage();
        this.data = data;
    }

}
