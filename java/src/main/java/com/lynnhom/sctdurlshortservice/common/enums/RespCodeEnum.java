package com.lynnhom.sctdurlshortservice.common.enums;

import lombok.Getter;

/**
  * 请求返回码枚举类
  *
  * @author: lynnhom
  * @date: 4:48 下午 2021/1/4
  */
public enum RespCodeEnum {
    /**
     * 返回码枚举值
     */
    REQUEST_SUCCESS("0000", "请求成功"),
    SERVER_ERROR("-1","服务异常,请联系管理员"),
    URL_INVALID("-2", "链接地址非法"),
    URL_SHORTEN_EXCEPTION("-3", "短链接转换错误"),
    SHORT_URL_NOT_EXIST("-4", "短链接不存在"),
    SHORT_URL_EXPIRED("-5", "短链接不存在"),
    SHORT_URL_INVALID("-6", "短链接地址过期"),
    EXPIRE_TIME_EARLIER_THAN_NOW("-7", "短链接失效日期早于当前日期"),
    TOKEN_INVALID("-8", "Token验证失败")
    ;
    @Getter
    private final String code;
    @Getter
    private final String message;

    RespCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
