package com.diode.interview.domain.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@AllArgsConstructor
public enum TransformerEnum {
    MD5(1, "MD5"),
    ;

    @Getter
    private final int code;
    @Getter
    private final String type;

    private static final Map<Integer, TransformerEnum> codeMap = new HashMap<>();
    private static final Map<String, TransformerEnum> descMap = new HashMap<>();

    static {
        for (TransformerEnum value : TransformerEnum.values()) {
            if(Objects.isNull(value)){
                continue;
            }
            codeMap.put(value.getCode(), value);
            descMap.put(value.getType(), value);
        }
    }

    public static TransformerEnum getByCode(Integer code){
        return codeMap.get(code);
    }

    public static TransformerEnum getByType(String type){
        return descMap.get(type);
    }
}
