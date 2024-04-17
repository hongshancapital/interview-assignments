package com.diode.interview.api.models.response;

import com.diode.interview.api.models.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Data
@ApiModel("返回体")
public class BaseResponse<T> {
    @ApiModelProperty("状态码, 详见ResponseCodeEnum")
    int code;
    @ApiModelProperty("返回体消息")
    String msg;
    @ApiModelProperty("返回体数据")
    T data;

    public static <T> BaseResponse<T> success(T data){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setMsg(ResponseCodeEnum.SUCCESS.getDesc());
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> error(ResponseCodeEnum codeEnum, String msg){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(codeEnum.getCode());
        response.setMsg(codeEnum.getDesc() + ":" + msg);
        return response;
    }
}
