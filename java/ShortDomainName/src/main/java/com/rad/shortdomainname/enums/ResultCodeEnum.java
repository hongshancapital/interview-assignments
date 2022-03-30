package com.rad.shortdomainname.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    INNER_ERROR(500, "内部错误");

    private int code;
    private String desc;

    ResultCodeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }


}
