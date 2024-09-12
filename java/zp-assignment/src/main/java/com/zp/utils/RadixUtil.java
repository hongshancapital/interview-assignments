package com.zp.utils;

/**
 * 进制转换工具类
 */
public class RadixUtil {

    /**
     * 进制转换
     *
     * @param chars   字符数组
     * @param number  数字
     * @param toRadix 目标进制类型
     * @return
     */
    public static String transRadix(char[] chars, long number, int toRadix) {
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            sb.append(chars[(int) (number % toRadix)]);
            number = number / toRadix;
        }
        return sb.reverse().toString();

    }
}
