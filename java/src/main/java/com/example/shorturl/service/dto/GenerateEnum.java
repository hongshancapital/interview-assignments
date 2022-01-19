package com.example.shorturl.service.dto;

import com.example.shorturl.utils.BaseEnum;

/**
 * @author yyp
 * @date 2022/1/16 10:15
 */
public enum GenerateEnum implements BaseEnum<String, String> {
    /**
     * 生成器类型
     */
    RANDOM_GENERATOR("RANDOM", "随机生成器"),
    INCREMENT_GENERATOR("INCREMENT", "自增生成器")
    ;

    private String code;
    private String val;

    GenerateEnum(String code, String val) {
        this.code = code;
        this.val = val;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getVal() {
        return this.val;
    }
}
