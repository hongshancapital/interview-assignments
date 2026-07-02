package com.scdt.china.shortdomain.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: CZ
 * @Date: 2022/1/22 12:18
 * @Description:
 */
public class ConversionUtil {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SCALE = 62;
    private static final int MIN_LENGTH = 7;

    /**
     * 10进制数字转62进制
     *
     * @param num 输入10进制数字
     * @return 输出62进制字符串
     */
    public static String encode10To62(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            //对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % SCALE).intValue();
            sb.append(CHARS.charAt(remainder));
            //除以进制数，获取下一个末尾数
            num = num / SCALE;
        }
        sb.append(CHARS.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, MIN_LENGTH, '0');
    }

    /**
     * 62进制转为数字
     *
     * @param str 输入62进制字符串
     * @return 输出10进制数字
     */
    public static long decode62To10(String str) {
        //将 0 开头的字符串进行替换
        str = str.replace("^0*", "");
        long value = 0;
        char tempChar;
        int tempCharValue;
        for (int i = 0; i < str.length(); i++) {
            //获取字符
            tempChar = str.charAt(i);
            //单字符值
            tempCharValue = CHARS.indexOf(tempChar);
            //单字符值在进制规则下表示的值
            value += (long) (tempCharValue * Math.pow(SCALE, str.length() - i - 1));
        }
        return value;
    }
}