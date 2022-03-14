/**
 * this is a test project
 */

package com.example.interviewassgnments.utils;

import java.security.SecureRandom;

/**
 *
 * @Auther: maple
 * @Date: 2022/1/19 10:14
 * @Description: com.example.interviewassgnments.utils
 * @version: 1.0
 */
public class CreateShortCodeUtils {
    //默认字符串的长度，左补TranCode的第一个元素
    private static int DEFAULT_TRANCODE_LENGTH = 4;

    // 字符码，可根据需要自行添加
    private static char[] TranCode = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'};

    /**
     * 获取TranCode的长度
     *
     * @return int
     */
    public static Integer TranCodeLength() {
        return TranCode.length;
    }


    /**
     * Integer转换成字符串编码
     * 将Integer类型转化对应成一个短字符串
     *
     * @param num Integer
     * @return String
     */
    public static String integer2ShortCode(Integer num) {
        return integer2ShortCode(num, DEFAULT_TRANCODE_LENGTH);
    }

    /**
     * Integer转换成字符串编码
     * 将Integer类型转化对应成一个短字符串
     *
     * @param num Integer
     * @return String
     */
    private static String integer2ShortCode(Integer num, Integer strLength) {
        if (num < 0) {
            return "";
        }
        String revers = "";
        boolean flag = true;
        while (flag) {
            if (num <= TranCodeLength()) {
                revers = TranCode[num] + revers;
                flag = false;
            } else {
                Integer merch = num / TranCodeLength();
                Integer remainder = num % TranCodeLength();

                if (merch > 0) {
                    revers = TranCode[remainder] + revers;
                    num = merch;
                } else if (merch == 0) {
                    revers = TranCode[remainder] + revers;
                    flag = false;
                }
            }
        }

        while (revers.length() < strLength) {
            revers = TranCode[0] + revers;
        }

        return revers;
    }

}
