package com.xiaoxi666.tinyurl.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/10
 * @Version: 1.0
 * @Description: 响应体
 */
@Setter
@Getter
@ApiModel(description = "响应对象")
public class Response implements Serializable {
    /**
     * 响应码
     */
    @ApiModelProperty(value = "响应码", name = "code", required = true, example = "10001")
    private int code;
    /**
     * 响应信息，主要用于错误信息提示
     */
    @ApiModelProperty(value = "响应信息，主要用于错误信息提示", name = "msg", required = true, example = "无效入参" )
    private String msg;
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据", name = "data", required = true)
    private Object data;

    public Response() {
    }

    public Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Response success(Object data) {
        return new Response(CodeMsg.SUCCESS.getCode(), CodeMsg.SUCCESS.getMsg(), data);
    }

    public static Response error(CodeMsg codeMsg, String... extraMsg) {
        String msg = String.format("%s[%s]", codeMsg.getMsg(), String.join(",", extraMsg));
        return new Response(codeMsg.getCode(), msg, null);
    }

    /**
     * 响应码和信息枚举
     */
    public enum CodeMsg {
        SUCCESS(200, "成功"),
        INNER_ERROR(10000, "服务器内部错误"),
        INVALID_PARAM(10001, "无效入参"),
        ID_EXHAUSTED(10002, "发号器用尽"),
        OP_NOT_ALLOWED(10003, "不允许的操作"),
        UN_FOUND(10004, "未找到");

        /**
         * 响应码
         */
        private int code;
        /**
         * 响应信息，主要用于错误信息提示
         */
        private String msg;

        CodeMsg(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}


