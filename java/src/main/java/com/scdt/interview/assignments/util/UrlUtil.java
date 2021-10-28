package com.scdt.interview.assignments.util;

import com.scdt.interview.assignments.constants.LinkUrlConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class UrlUtil {
    public static String shortUrl(String longUrl) {
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"};

        // 对传入网址进行 MD5 加密
        String longUrlMd5Hex = DigestUtils.md5Hex(LinkUrlConstants.SALT + longUrl);
        String[] shortUrl = new String[8];

        for (int i = 0; i < 8; i++) {
            String substr = longUrlMd5Hex.substring(i * 4, i * 4 + 4);
            long hexLong = Long.parseLong(substr, 16);
            // 把得到的值与 0x0000003D=61 进行位与运算，取得字符数组 chars 索引
            long index = 0x0000003D & hexLong;
            // 把取得的字符相加
            String outChars = chars[(int) index];
            // 把字符串存入对应索引的输出数组
            shortUrl[i] = outChars;
        }
        return StringUtils.join(shortUrl);
    }
}
