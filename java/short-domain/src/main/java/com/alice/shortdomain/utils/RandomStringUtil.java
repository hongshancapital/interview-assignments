package com.alice.shortdomain.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机字符串生成工具
 *
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/15 17:58
 */
public class RandomStringUtil {


    /**
     * 基准数据
     */
    private final static String[] BASE_STR = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };


    /**
     * 获取随机字符串
     *
     * @param length 随机字符串长度
     * @return
     */
    public static String randomStr(int length) {
        StringBuffer sb = new StringBuffer();
        while (sb.length() < length) {
            sb.append(BASE_STR[ThreadLocalRandom.current().nextInt(0, BASE_STR.length)]);
        }
        return sb.toString();
    }

}
