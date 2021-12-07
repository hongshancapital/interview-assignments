package com.scdt.aeolus.common;

import org.springframework.beans.factory.annotation.Value;

public class UrlConverter {
    public static final String ALPHABET = "Mheo9PI2qNs5Zpf80TBn7lmRbtQ4YKXHvwAEWxuzdra316OJigGLSVUCyFjkDc";
    public static final int BASE = ALPHABET.length();

    /**
     * 10位数字转为base62字符串
     * @param num
     * @return
     */
    public static String idToStr(Long num, Integer reservedLength) {
        StringBuilder str = new StringBuilder();
        while (num > 0) {
            str.insert(0, ALPHABET.charAt((int) (num % BASE)));
            num = num / BASE;
        }
        String base62Str = str.toString();
        if (base62Str.length() > reservedLength) {
            return base62Str.substring(base62Str.length() - reservedLength);
        }
        return base62Str;
    }
}