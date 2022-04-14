package com.mjl.utils;

import org.apache.commons.codec.digest.DigestUtils;


public class ShortUrlUtil {

    private final static int SHORT_URL_LENGTH= 8;
    private final static String[] SHORT_URL_CHARS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
            "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    };
    private final static int SHORT_URL_CHAR_NUM = 62;

    public static String genShortUrlSuffix(String url, String salt) {
        String md5Result = DigestUtils.md5Hex(salt + url);
        // 由于只需要8位长度的短链接，所以可以只使用hash值的前若干位
        md5Result = md5Result.substring(0, SHORT_URL_LENGTH * 6 / 4 + 1);
        long hexResult = Long.parseLong(md5Result, 16);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            stringBuilder.append(SHORT_URL_CHARS[(int)(hexResult % SHORT_URL_CHAR_NUM)]);
            hexResult = hexResult/62;
        }
        return stringBuilder.toString();
    }
}
