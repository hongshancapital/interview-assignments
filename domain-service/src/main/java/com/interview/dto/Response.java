package com.interview.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: nyacc
 * @Date: 2021/12/17 13:29
 */

@Data
@ApiModel(description = "通用返回对象")
public class Response<T> {

    @ApiModelProperty("返回的业务对象")
    private T data;

    @ApiModelProperty(value = "错误代码，0--成功，其他失败", example = "0")
    private String code = "0";

    @ApiModelProperty(value = "返回信息描述", example = "success")
    private String msg = "success";

    private static final String SUCCESS_CODE="0";

    private static final String SYS_ERROR="9999";

    public static Response buildSuccess(Object data){
        Response response=new Response();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }

    public static Response buildFail(String code,String msg){
        Response response=new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static Response buildFail(String msg){
        return buildFail(SYS_ERROR, msg);
    }
}
