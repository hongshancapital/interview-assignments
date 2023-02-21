package cn.sequoiacap.shorturl.controller;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Response")
public class Response<T> {
    @Schema(description = "响应码")
    private Integer code;
    @Schema(description = "提示信息, 非正常返回可以给用户友好提示")
    private String message;
    @Schema(description = "响应数据")
    private T data;

    public Response() {
    }

    public Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <R> Response<R> success(R data) {
        return new Response<>(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMessage(), data);
    }

    public static <R> Response<R> fail(ResponseStatus status) {
        return new Response<>(status.getCode(), status.getMessage(), null);
    }

    public static <R> Response<R> fail(ResponseStatus status, String message) {
        return new Response<>(status.getCode(), message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
