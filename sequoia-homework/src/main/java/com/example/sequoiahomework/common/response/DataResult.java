package com.example.sequoiahomework.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Irvin
 * @description 返回结果封装
 * @date 2021-10-09
 */
@Data
@ApiModel(value = "返回结果外层对象", description = "返回结果外层对象")
@NoArgsConstructor
public class DataResult<T> implements Serializable {

    /**
     * 最大页数
     */
    @ApiModelProperty(value = "最大页数")
    private long total;

    /**
     * 200 成功 300 值逻辑校验未通过 如 用户名不对 密码不对等 400 接收参数不正确 500 服务错误
     */
    @ApiModelProperty(value = "200 成功 300 值逻辑校验未通过 如 用户名不对 密码不对等 400 接收参数不正确 500 服务错误")
    private long code;

    /**
     * 存放查询出来的结果
     */
    @ApiModelProperty(value = "存放查询出来的结果")
    private T data;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息")
    private String message;

    public DataResult(long total, long code, T data, String message) {
        this.total = total;
        this.code = code;
        this.data = data;
        this.message = message;
    }

}
