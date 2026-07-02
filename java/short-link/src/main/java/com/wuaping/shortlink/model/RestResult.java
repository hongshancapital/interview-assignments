package com.wuaping.shortlink.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 接口层统一返回报文体
 *
 * @author Aping
 * @since 2022/3/18 14:42
 */
@Getter
@ApiModel("接口层统一返回报文体")
public class RestResult<T> {

    private final static int SUCCESS_CODE = 0;
    private final static String SUCCESS_MSG = "success";

    @ApiModelProperty("0为成功，非0异常")
    private Integer code;

    @ApiModelProperty("描述")
    private String message;

    private T data;

    public RestResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(T data) {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MSG;
        this.data = data;
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(data);
    }

    public static <T> RestResult<T> error(Integer code, String message) {
        return new RestResult<>(code, message);
    }

}
