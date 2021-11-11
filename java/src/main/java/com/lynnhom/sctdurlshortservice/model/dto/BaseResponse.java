package com.lynnhom.sctdurlshortservice.model.dto;

import com.lynnhom.sctdurlshortservice.common.enums.RespCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接口返回格式
 *
 * @author: lynnhom
 * @date: 4:00 下午 2021/7/18
 */
@Data
@ApiModel
public class BaseResponse<T> {
    @ApiModelProperty("返回码")
    private String code;
    @ApiModelProperty("返回信息")
    private String msg;
    @ApiModelProperty("返回数据")
    private T data;

    public static <T> BaseResponse<T> success() {
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(RespCodeEnum.REQUEST_SUCCESS.getCode());
        res.setMsg(RespCodeEnum.REQUEST_SUCCESS.getMessage());
        res.setData(null);
        return res;
    }

    public static <T> BaseResponse<T> success(T input) {
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(RespCodeEnum.REQUEST_SUCCESS.getCode());
        res.setMsg(RespCodeEnum.REQUEST_SUCCESS.getMessage());
        res.setData(input);
        return res;
    }

    public static <T> BaseResponse<T> success(String message, T input) {
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(RespCodeEnum.REQUEST_SUCCESS.getCode());
        res.setMsg(message);
        res.setData(input);
        return res;
    }

    public static <T> BaseResponse<T> fail(T input) {
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(RespCodeEnum.SERVER_ERROR.getCode());
        res.setMsg(RespCodeEnum.SERVER_ERROR.getMessage());
        res.setData(null);
        return res;
    }

    public static <T> BaseResponse<T> fail(String code, String msg) {
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(null);
        return res;
    }
}
