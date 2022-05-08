package com.coderdream.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 返回结果集
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
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
    private Boolean success;

    @ApiModelProperty("数据")
    private T data;

    public Result(Boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}