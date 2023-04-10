package com.hongshan.work.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 进制转换器
 */
public enum ConversionUtils {

    /**
     * 单例
     */
    X;

    private static final String CHARS = "oNWxUYwrXdCOIj4ck6M8RbiQa3H91pSmZTAh70zquLnKvt2VyEGlBsPJgDe5Ff";
    private static final int SCALE = 62;
    private static final int MIN_LENGTH = 5;

    /**
     * 数字转62进制
     */
    public String encode62(long num) {
        StringBuilder builder = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            remainder = Long.valueOf(num % SCALE).intValue();
            builder.append(CHARS.charAt(remainder));
            num = num / SCALE;
        }
        builder.append(CHARS.charAt(Long.valueOf(num).intValue()));
        String value = builder.reverse().toString();
        return StringUtils.leftPad(value, MIN_LENGTH, '0');
    }
}