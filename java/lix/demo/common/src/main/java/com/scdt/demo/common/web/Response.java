package com.scdt.demo.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    /** 状态码 */
    private String code;

    /** 描述信息 */
    private String message;

    /** 具体数据 */
    private T data;

    private Response() { }

    private Response<T> code(String code) {
        this.code = code;
        return this;
    }

    private Response<T> message(String message) {
        this.message = message;
        return this;
    }

    public Response<T> data(T data) {
        this.data = data;
        return this;
    }

    public static <T> Response<T> ok(T data) {
        return new Response<T>()
                .code(CodeMessage.SUCCESS.getCode())
                .message(CodeMessage.SUCCESS.getMessage())
                .data(data);
    }

    public static <T> Response<T> fail(CodeMessage cm) {
        return new Response<T>()
                .code(cm.getCode())
                .message(cm.getMessage());
    }
}
