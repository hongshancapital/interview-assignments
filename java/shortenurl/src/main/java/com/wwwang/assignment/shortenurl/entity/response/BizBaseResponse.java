package com.wwwang.assignment.shortenurl.entity.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class BizBaseResponse<T extends Serializable> implements Serializable {
    private static final String SUCCESS_MSG = "操作成功";
    private static final String ERROR_MSG = "系统错误，操作失败";

    private int code;
    private String message;
    private T data;

    public BizBaseResponse(T data, String message) {
        this(BizEnum.SUCCESS, message, data);
    }

    public BizBaseResponse(BizEnum errorCode, String message) {
        this(errorCode, message, null);
    }

    public BizBaseResponse(BizEnum errorCode, String message, T data) {
        this.code = errorCode.getCode();
        this.message = message;
        this.data = data;
    }

    public static <T extends Serializable> BizBaseResponse<T> success(T data) {
        return new BizBaseResponse(data, SUCCESS_MSG);
    }


    public static BizBaseResponse operationFailed(String message) {
        return new BizBaseResponse(BizEnum.OPERATION_FAILED, message);
    }

    public static BizBaseResponse operationFailed() {
        return new BizBaseResponse(BizEnum.OPERATION_FAILED, ERROR_MSG);
    }

}
