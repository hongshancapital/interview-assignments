package com.zoujing.shortlink.exception;

public enum ShortLinkResultEnum {
    SUCCESS("SUCCESS", "请求成功"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
    REQUEST_PARAM_ERROR("REQUEST_PARAM_ERROR", "请求参数错误"),
    CONFIG_ERROR("CONFIG_ERROR", "启动参数错误"),
    ID_GENERATOR_ERROR("ID_GENERATOR_ERROR", "发号器失败"),
    SHORT_LINK_NOT_IN_CACHE("SHORT_LINK_NOT_IN_CACHE", "短链接不在缓存中"),
    ;


    private String code;
    private String desc;

    ShortLinkResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
