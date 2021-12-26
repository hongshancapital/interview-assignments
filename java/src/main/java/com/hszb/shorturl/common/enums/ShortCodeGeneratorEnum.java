package com.hszb.shorturl.common.enums;

import lombok.AllArgsConstructor;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/19 12:21 下午
 * @Version: 1.0
 * @Description:
 */

@AllArgsConstructor
public enum ShortCodeGeneratorEnum {

    ID_GENERATOR("id", "ID自增生成器策略"),
    RANDOM_GENERATOR("md5", "随机生成器策略");

    public String strategy;

    public String desc;

    public static ShortCodeGeneratorEnum getStructureEnum(String statNo) {
        if (null == statNo) {
            return null;
        }
        for (ShortCodeGeneratorEnum result : ShortCodeGeneratorEnum.values()) {
            if (result.strategy.equals(statNo)) {
                return result;
            }
        }
        return null;
    }
}
