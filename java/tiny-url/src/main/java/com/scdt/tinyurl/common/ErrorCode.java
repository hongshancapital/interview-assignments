package com.scdt.tinyurl.common;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ILLEGAL_REQUEST_PARAM("401","非法请求参数"),
    TINY_URL_NOT_FOUND_OR_EXPIRED("404","短链接不存在或已过期"),
    INVALID_ID_ERROR("402","非法的TinyUrl"),
    ID_MOVED_ERROR("405","ID非当前实例生成"),

    SERVICE_UNKNOWN_ERROR("500","服务器未知错误"),

    ID_EXCEEDED_ERROR("501","请求ID达到上限,请稍后重试"),
    NETWORK_OVERFLOW_ERROR("502","网络流量异常，请稍后重试！"),
    INVALID_BROKER_ID_ERROR("503","非法的broker-id编号"),

    SUCCESS("200", "请求成功");

    private String code;

    private String msg;

}
