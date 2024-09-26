package com.example.shorturl.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author yyp
 * @date 2022/1/16 11:16
 */
@Data
@ApiModel
@Service
public class ResponseResult<T> {
    @ApiModelProperty(value = "状态码", required = true)
    private String code;
    @ApiModelProperty(value = "状态码对应的提示信息", required = true)
    private String msg;
    @ApiModelProperty(value = "返回结果", required = false, notes = "仅在成功时不为空")
    private T data;

    public ResponseResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult() {
    }

    public static <T> ResponseResult<T> success(T t) {
        return new ResponseResult<>("success", "success", t);
    }

    public static <T> ResponseResult<T> fail(String message) {
        return new ResponseResult<>("fail", message, null);
    }

    public static ResponseResult<String> fail() {
        return fail("");
    }
}
