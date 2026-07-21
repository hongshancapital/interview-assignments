package com.rad.shortdomainname.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xukui
 * @program: ShortDomainName
 * @description: Http的返回结果的结构
 * @date 2022-03-19 14:57:33
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
