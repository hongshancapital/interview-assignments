package com.hello.tinyurl.util;

import org.springframework.util.StringUtils;

/**
 * @author: Shuai
 * @date: 2021-7-14 12:24
 * @description:
 */
public class UrlConvertUtil {

    private static final char[] map = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'
    };

    /**
     * 通过id生成shortUrl
     */
    public static String num2String(Long id) throws Exception {
        if (id == null)
            throw new Exception("input id is empty.");
        if (id >= 218340105584896L)  // 62L * 62 * 62 * 62 * 62 * 62 * 62 * 62
            throw new Exception("out of maximum tiny url length.");
        StringBuilder tinyUrl = new StringBuilder();
        while (id != 0) {
            tinyUrl.append(map[(int) (id % 62)]);
            id = id / 62;
        }
        tinyUrl.reverse();
        return tinyUrl.toString();
    }

    /**
     * 通过shortUrl生成id
     */
    public static long string2Number(String tinyUrl) throws Exception {
        if (!StringUtils.hasText(tinyUrl))
            throw new Exception("input string is empty.");
        if (tinyUrl.length() > 9)
            throw new Exception("input string " + tinyUrl + " longer then 8 characters.");
        char[] chars = tinyUrl.toCharArray();
        long id = 0L;
        for (int i = 0; i < tinyUrl.length(); i++) {
            if ('a' <= chars[i] && chars[i] <= 'z') {
                id = id * 62 + chars[i] - 'a';
            } else if ('A' <= chars[i] && chars[i] <= 'Z') {
                id = id * 62 + chars[i] - 'A' + 26;
            } else if ('0' <= chars[i] && chars[i] <= '9') {
                id = id * 62 + chars[i] - '0' + 52;
            } else {
                throw new Exception("contains illegal character '" + chars[i] + "'.");
            }
        }
        return id;
    }
}
