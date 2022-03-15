package com.example.shorturl.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yingchen
 * @date 2022/3/15
 */
@ApiModel("返回结果模型")
public class ResponseDTO<T> {
    @ApiModelProperty("错误码，0表示成功，非0表示失败")
    private int code;
    @ApiModelProperty("错误信息")
    private String msg;
    @ApiModelProperty("请求结果")
    private T data;

    public static <T> ResponseDTO<T> success(T data) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setCode(0);
        responseDTO.setData(data);
        return responseDTO;
    }

    public static <T> ResponseDTO<T> error(String msg) {
        return error(-1, msg);
    }

    public static <T> ResponseDTO<T> error(int code, String msg) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setCode(code);
        responseDTO.setMsg(msg);
        return responseDTO;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
