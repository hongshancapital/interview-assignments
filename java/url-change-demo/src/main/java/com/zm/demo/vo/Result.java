package com.zm.demo.vo;

import com.zm.demo.enums.ResultCodeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @ClassName Result
 * @Author zhaomin
 * @Date 2021/10/29 17:15
 **/
@AllArgsConstructor
@Getter
@ApiModel(value="基础返回类",description="基础返回类")
public class Result {

    @ApiModelProperty(value="状态码", example="0")
    Integer code;

    @ApiModelProperty(value="返回的提示信息", example="执行成功")
    String messgae;

    @ApiModelProperty(value="返回的数据")
    Object data;

    public static Result success(Object data){
        return new Result(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data );
    }

    public static Result error(ResultCodeEnum resultCodeEnum){
        return new Result(resultCodeEnum.getCode(), resultCodeEnum.getMessage(), null);
    }

    public static Result error(int code, String message){
        return new Result(code,  message, null);
    }
}
