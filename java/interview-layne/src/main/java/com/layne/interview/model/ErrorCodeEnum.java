package com.layne.interview.model;

import lombok.Getter;

/**
 * 错误码
 *
 * @author layne
 * @version UrlManage.java, v 0.1 2022/1/17 23:11 下午
 */
@Getter
public enum ErrorCodeEnum {

    BAD_REQUEST("BAD_REQUEST", "入参请求错误"),

    INTERNAL_ERROR("INTERNAL_ERROR", "系统内部错误"),

    REQUEST_DATA_NOT_EXIST("REQUEST_DATA_NOT_EXIST", "不存在请求的数据"),
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String description;

    ErrorCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
