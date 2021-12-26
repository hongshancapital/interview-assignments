package com.hszb.shorturl.manager.generate;

import com.hszb.shorturl.common.enums.ShortCodeGeneratorEnum;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/19 12:32 下午
 * @Version: 1.0
 * @Description: 短地址编号生成器构建工厂类
 */

public class ShortUrlGeneratorFactory {

    public static final char[] charArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static ShortCodeGenerator create (String stratege) {
        if (null == stratege) {
            return new IdShortUrlGenerator();
        }
        ShortCodeGenerator shortCodeGenerator = null;
        ShortCodeGeneratorEnum shortCodeGeneratorEnum = ShortCodeGeneratorEnum.getStructureEnum(stratege);
        switch (shortCodeGeneratorEnum) {
            case ID_GENERATOR:
                shortCodeGenerator = new IdShortUrlGenerator();
                break;
            case RANDOM_GENERATOR:
                shortCodeGenerator = new Md5ShortUrlGenerator();
                break;
        }
        return shortCodeGenerator;
    }
}
