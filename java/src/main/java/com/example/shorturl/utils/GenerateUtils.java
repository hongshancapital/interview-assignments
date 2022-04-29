package com.example.shorturl.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;

/**
 * @author yyp
 * @date 2022/1/16 17:43
 */
public class GenerateUtils {
    private static String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final Integer NUMBER_MODE = 62;

    public static Long MAX_VALUE = 218340105584896L;

    public static Long MIN_VALUE = 0L;

    private static final SecureRandom random = new SecureRandom();

    public static String formatUrlCode(String orgStr, Integer len) {
        return StringUtils.leftPad(orgStr, len, '0');
    }

    /**
     * 十进制转62进制（仅限正整数）
     *
     * @param num 十进制数字
     * @return java.lang.String
     */
    public static String longToSixtyTwo(long num) {
        if (num <= 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        //余数
        long remainder;
        while (num > 0) {
            remainder = num % NUMBER_MODE;
            //0-9
            if (remainder < 10) {
                sb.append((char) ('0' + remainder));
            }
            //A-Z
            else if (remainder < 36) {
                sb.append((char) ('A' + remainder - 10));
            }
            //a-z
            else {
                sb.append((char) ('a' + remainder - 36));
            }
            num = num / NUMBER_MODE;
        }
        //因为在上面的循环过程中，后一次循环本应是计算出来的高位字符，但是却被我们放在字符串的最后面，因此最终结果需要再反转一次
        return sb.reverse().toString();
    }

    /**
     * 62进制转十进制
     *
     * @param numStr 62进制字符串
     * @return
     */
    public static long sixtyTwoToLong(String numStr) {
        //最后转换完成之后的十进制数字
        long num = 0;
        //字符串中的具体某一个字符
        int idx;
        for (int i = 0; i < numStr.length(); i++) {
            idx = numStr.charAt(numStr.length() - 1 - i);
            if (idx >= 'a') {
                //idx = 'a' + remainder - 36，于是可以推导出：remainder = idx + 36 - 'a'
                //num = remainder * 62^i
                num += (idx + 36 - 'a') * Math.pow(NUMBER_MODE, i);
            } else if (idx >= 'A') {
                //idx = 'A' + remainder - 10，于是可以推导出：remainder = idx + 10 - 'A'
                num += (idx + 10 - 'A') * Math.pow(NUMBER_MODE, i);
            } else {
                //idx = '0' + remainder，于是可以推导出：remainder = idx - '0'
                num += (idx - '0') * Math.pow(NUMBER_MODE, i);
            }
        }
        return num;
    }

    public static Integer getRandomNumber(Integer n) {
        return random.nextInt(n);
    }

    public static Long getRandomNumber(Long start, Long end) {
        return RandomUtils.nextLong(start, end);
    }
}
