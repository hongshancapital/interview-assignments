package com.hongshang.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务通用返回值类型
 *
 * @author kobe
 * @date 2021/12/19
 */
@ApiModel("服务返回值对象")
@Data
public class ComResult {

    /**
     * 返回服务状态编码 200：表示成功   500：表示失败
     */
    @ApiModelProperty("服务状态编码")
    private Integer code;

    /**
     * 返回服务执行状态描述
     */
    @ApiModelProperty("服务执行状态描述")
    private String message;

    /**
     * 服务执行是否成功，true:成功， false：失败
     */
    @ApiModelProperty("服务执行是否成功，true:成功， false：失败")
    private Boolean isSuccess;

    /**
     * 返回值
     */
    @ApiModelProperty("返回地址值")
    private Object result;

    public ComResult(){ }

    public ComResult(Integer code, String message, Boolean isSuccess,Object result){
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
        this.result = result;
    }

    /**
     * 产生成功的返回对象
     *
     * @param message String
     * @return
     */
    public static ComResult SUCCESS(String message){
        return new ComResult(200,message,true,null);
    }

    /**
     * 产生失败的返回对象
     *
     * @param message
     * @return
     */
    public static ComResult FAIL(String message){
        return new ComResult(500,message,false,null);
    }


}
