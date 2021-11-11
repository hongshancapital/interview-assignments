package com.lynnhom.sctdurlshortservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @description: BASE62编码解码工具
 * @author: Lynnhom
 * @create: 2021-10-29 15:03
 **/

@Slf4j
public class Base62Util {
    public static final String ALPHABET = "Mheo9PI2qNs5Zpf80TBn7lmRbtQ4YKXHvwAEWxuzdra316OJigGLSVUCyFjkDc";
    public static final int BASE = ALPHABET.length();

    private Base62Util(){}

    /**
     * Base62编码
     * @param num
     * @return
     */
    public static String encode(Long num) {
        StringBuilder str = new StringBuilder();
        if (Objects.isNull(num) || num.longValue() < 0) {
            log.error("encode, base62 conversion num invalid, null or less than 0, num={}", num);
            throw new RuntimeException("服务异常");
        }
        // 10进制转62进制逻辑
        if (Objects.equals(num, 0L) ) {
            str.insert(0, ALPHABET.charAt(0));
        } else {
            while (num > 0) {
                str.insert(0, ALPHABET.charAt((int) (num % BASE)));
                num = num / BASE;
            }
        }
        return str.toString();
    }

    /**
     * Base62解码
     * @param str
     * @return
     */
    public static Long decode(String str) {
        Long num = 0L;
        for (int i = 0; i < str.length(); i++) {
            int index = ALPHABET.indexOf(str.charAt(i));
            if (index < 0) {
                log.error("decode, base62 decode string invalid, str={}", str);
                throw new RuntimeException("服务异常");
            }
            num = num * BASE + index;
        }
        return num;
    }
}
