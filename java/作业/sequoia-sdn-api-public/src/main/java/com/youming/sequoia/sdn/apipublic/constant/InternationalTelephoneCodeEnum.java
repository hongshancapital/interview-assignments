package com.youming.sequoia.sdn.apipublic.constant;

/**
 * 国际电话区号枚举
 */
public enum InternationalTelephoneCodeEnum {
    CHINA("86"), // 中国
    AMERICA("1"); // 美国
    private final String code;

    private InternationalTelephoneCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
