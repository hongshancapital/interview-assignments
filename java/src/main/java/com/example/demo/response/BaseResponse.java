package com.example.demo.response;

import lombok.Data;

@Data
public class BaseResponse<T> {

    /** 操作是否成功 */
    private boolean success;
    /** 操作代码 */
    private int code;
    /** 提示信息 */
    private String message;

    /** 返回的数据 */
    private T data;

    public BaseResponse() {
        this(ResultCode.SUCCESS);
    }

    public BaseResponse(ResultCode resultCode) {
        this.success = resultCode.isSuccess();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public BaseResponse(ResultCode resultCode, String message) {
        this.success = resultCode.isSuccess();
        this.code = resultCode.getCode();
        this.message = message;
    }

    public static <E> BaseResponse<E> success(E data) {
        BaseResponse<E> baseResponse = new BaseResponse<>();
        baseResponse.setData(data);
        return baseResponse;
    }

    public static BaseResponse<ResultCode> error(ResultCode resultCode) {
        return new BaseResponse<>(resultCode);
    }

    public static BaseResponse<ResultCode> error(ResultCode resultCode, String message) {
        return new BaseResponse<>(resultCode, message);
    }

}
