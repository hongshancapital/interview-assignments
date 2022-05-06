package com.lenfen.short_domain.api.entity;

import com.lenfen.short_domain.bean.ShortDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口响应实体
 */
@Getter
@Setter
@ApiModel(value = "响应数据")
public class ApiResponse {
    public static final int STATE_OK = 1;
    public static final int STATE_FAIL = 0;
    public static final int STATE_ERR = 10;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码", required = true, example = "1")
    private int code;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String message;

    /**
     * 数据域
     */
    @ApiModelProperty(value = "数据")
    private ShortDomain shortDomain;

    public static ApiResponse fail(String message) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(STATE_FAIL);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static ApiResponse err(String message) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(STATE_ERR);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static ApiResponse ok(ShortDomain data) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(STATE_OK);
        apiResponse.setMessage("成功");
        apiResponse.setShortDomain(data);
        return apiResponse;
    }
}
