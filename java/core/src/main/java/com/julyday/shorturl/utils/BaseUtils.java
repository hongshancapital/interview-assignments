package com.julyday.shorturl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/*
 * 工具类.
 */
public class BaseUtils {
    private static final Logger log = LoggerFactory.getLogger(BaseUtils.class);
    private static final char[] CHARSET_62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /*
     * 十进制转六十二进制.
     */
    public static String encode10To62(Long seq) {
        StringBuilder stringBuilder = new StringBuilder();

        do {
            int remainder = (int)(seq % 62L);
            stringBuilder.append(CHARSET_62[remainder]);
            seq /= 62L;
        } while(seq != 0L);

        return stringBuilder.reverse().toString();
    }

    /*
     * 六十二进制转十进制.
     */
    public static long decode62To10(String str) {
        if (str == null || str.length() == 0 || str.length() > 8) {
            log.error("六十二进制转十进制超过长度限制,字符串为:{}", str);
            return -1L;
        }
        BigDecimal sum = new BigDecimal(0L);
        int len = str.length();

        for(int i = 0; i < len; ++i) {
            sum = sum.add((new BigDecimal(indexDigits62(str.charAt(len - i - 1)))).multiply((new BigDecimal(62)).pow(i)));
        }

        return sum.longValue();
    }

    private static int indexDigits62(char ch) {
        for(int i = 0; i < CHARSET_62.length; ++i) {
            if (ch == CHARSET_62[i]) {
                return i;
            }
        }

        return -1;
    }
}
