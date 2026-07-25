package com.scdt.job.lsx.enums;

import lombok.Getter;

/**
 * @author lsx
 */

@Getter
public enum ErrorCodeEnum implements BaseErrorEnumInterface{

    Success(200, "成功"),
    /**
     * 未授权
     */
    NotAuthorization(401, "未授权"),
    /**
     * 参数非法
     */
    InvalidParams(406, "参数非法"),
    /**
     * 请求的资源不存在
     */
    ResourceNotFound(407, "请求的资源不存在"),
    /**
     * 请求的文件不存在
     */
    FileNotExisted(408, "请求的文件不存在"),
    /**
     * 网络异常
     */
    NetworkError(409, "网络异常"),
    /**
     * 程序内部错误
     */
    InternalErr(500, "内部错误");

    /**
     * 错误码 code
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
