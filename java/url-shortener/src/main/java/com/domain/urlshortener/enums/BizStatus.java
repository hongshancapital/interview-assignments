package com.domain.urlshortener.enums;

import lombok.Getter;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 21:16
 */
@Getter
public enum BizStatus {

    /** 成功 */
    SUCCESS("0000", "操作成功"),
    /** 失败 */
    FAILED("9999", "操作失败"),

    /** 无效请求 */
    BAD_REQUEST("9001", "无效请求");

    private String code;
    private String description;

    BizStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
