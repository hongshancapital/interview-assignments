package com.duoji.shortlink.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月19日 12:53:00
 */
@Data
@Slf4j
@ApiModel
public class Result<T> {

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("信息")
    private String message;

    @ApiModelProperty("是否成功")
    private boolean success;

    @ApiModelProperty("数据")
    private T data;

    public Result(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
