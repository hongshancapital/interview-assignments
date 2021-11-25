package com.wigo.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wigo.chen
 * @date 2021/7/27 9:23 下午
 * Introduction: http交互结果类
 */
@Data
@ApiModel
public class Result {
    /**
     * 接口状态编码
     */
    @ApiModelProperty("接口状态编码")
    public int code;
    /**
     * 接口错误信息描述
     */
    @ApiModelProperty("错误信息描述")
    public String msg;
    /**
     * 接口返回结果信息描述
     */
    @ApiModelProperty("返回的链接")
    public Object data;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 组装错误返回信息
     *
     * @param msg 接口错误信息描述
     * @return com.wigo.core.Result 接口返回实体
     * @author wigo.chen
     * @date 2021/7/27 9:25 下午
     **/
    public static Result fail(String msg) {
        return new Result(Constant.FAIL, msg);
    }

    /**
     * 组装接口成功返回信息
     *
     * @param data 接口返回结果信息描述
     * @return com.wigo.core.Result 接口返回实体
     * @author wigo.chen
     * @date 2021/7/27 9:26 下午
     **/
    public static Result success(Object data) {
        return new Result(Constant.SUCCESS, Constant.SUCCESS_MSG, data);
    }
}
