package com.sequoia.interview.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
* @Description: return msg
* @Author: yhzhang
* @Date: 2022/8/13
*/
@AllArgsConstructor
@Getter
@Setter
@ApiModel
public class Result<T> {
    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("信息")
    private String msg;

    @ApiModelProperty("数据")
    private T data;
}
