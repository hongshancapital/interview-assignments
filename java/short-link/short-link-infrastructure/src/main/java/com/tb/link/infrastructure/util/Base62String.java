package com.tb.link.infrastructure.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author andy.lhc
 * @date 2022/4/16 21:39
 */
public class Base62String {


    public static final String BASE_62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 62进制可用的字符串，可打散排列
     */
    private static final String[] DIGITS = {"A", "a", "Z", "0", "j", "Q", "9", "k", "P", "l", "O", "m", "N", "n", "M", "o", "L", "p", "K", "q", "J", "r", "I", "s", "H", "t", "G", "u", "F", "V", "4", "f", "U", "5", "g", "T", "6", "h", "S", "7", "i", "R", "8", "v", "E", "w", "D", "x", "C", "y", "B", "z", "b", "Y", "1", "c", "X", "2", "d", "W", "3", "e"};

    private static Map<String, Integer> DIGITS_TABLE = new HashMap<String, Integer>();
    /**
     * 最大可以达到8的排列组合个可选值
     * 暂时这么配，后续可通过开关平台统一配置
     */
    private static final int SHORT = 8;

    //数组转成table表，方便转成62进制数字
    static {
        for (int i = 0; i < DIGITS.length; i++) {
            DIGITS_TABLE.put(DIGITS[i], i);
        }
    }

    /**
     * 8位可存万亿以上，足够使用
     *
     * @param id
     * @return
     */
    public static String generate(long id) {
        String shortName = "";
        int digitsLength = DIGITS.length;
        if (0 < id) {
            if (id < digitsLength + 1 && id > 0) {
                shortName = DIGITS[(int) (id - 1)];
            } else if (id > digitsLength) {
                long mod = 0;
                long multiplier = 0;
                boolean bool = false;
                for (int j = SHORT; j > 0; j--) {
                    multiplier = (long) (id / Math.pow(digitsLength, j));
                    if (multiplier > 0 && multiplier < digitsLength && id >= digitsLength) {
                        shortName += DIGITS[(int) multiplier];
                        bool = true;
                    } else if (bool && multiplier == 0) {
                        shortName += DIGITS[0];
                    } else if (id < digitsLength) {
                        shortName += DIGITS[(int) mod];
                    }
                    mod = (long) (id % Math.pow(digitsLength, j));
                    id = mod;
                }
            }
        }
        return shortName;
    }

    public static String generate8Length(long id) {
        String base62 = generate(id);
        StringBuilder sb = new StringBuilder();
        sb.append(base62);
        if (base62.length() < SHORT) {
            int randomLength = SHORT - base62.length();
            if (randomLength > 0) {
                Random r = new Random();
                for (int i = 0; i < randomLength; i++) {
                    sb.append(DIGITS[r.nextInt(DIGITS.length)]);//取DIGITS表里随机一个字符
                }
            }
        }
        return sb.toString();
    }

    /**
     * 按序列转成62进制字符，同时拼上一个randomLenght长度的随机字符
     * 可生成长于8位的字符串
     *
     * @param id
     * @param randomLength
     * @return
     */
    public static String generate(long id, int randomLength) {
        StringBuffer sb = new StringBuffer();
        sb.append(generate(id));
        if (randomLength > 0) {
            Random r = new Random();
            for (int i = 0; i < randomLength; i++) {
                sb.append(DIGITS[r.nextInt(DIGITS.length)]);//取DIGITS表里随机一个字符
            }
        }
        String str = sb.toString();
        if (str.length() > SHORT) {
            int len = str.length() - SHORT;
            return str.substring(len);
        }
        return str;
    }


}

