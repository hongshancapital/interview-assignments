package com.zc.shorturl.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 62进制转换工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseConvertUtils {
    private static final long BASE = 62L;

    private static final int BASE62_MAX_LENGTH = 8;

    private static final char[] CHARS = "LBClqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcp".toCharArray();

    /**
     * 10进制转62进制，结果未排序
     * @param number 10进制数
     * @return 62进制
     */
    public static String decimal2Base62(long number){
        if (number < 0) {
            return "";
        }

        if (number == 0) {
            return String.valueOf(CHARS[0]);
        }

        StringBuilder base62 = new StringBuilder();

        while(number > 0){
            base62.append(CHARS[(int)(number % BASE)]);
            number /= BASE;
        }

        return base62.toString();
    }

}
