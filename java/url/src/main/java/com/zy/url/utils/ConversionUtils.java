package com.zy.url.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 反转
 */
public class ConversionUtils {

    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;
    private static int minLength = 5;

    //10进制数字转62进制
    public static String base10EncodeTo62(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > scale - 1) {
            //对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            //除以进制数，获取下一个末尾数
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, minLength, '0');
    }

    //62进制转为10进制数字
    public static long str62DecodeTo10(String str) {
        //将 0 开头的字符串进行替换
        str = str.replace("^0*", "");
        long value = 0;
        char tempChar;
        int tempCharValue;
        for (int i = 0; i < str.length(); i++) {
            //获取字符
            tempChar = str.charAt(i);
            //单字符值
            tempCharValue = chars.indexOf(tempChar);
            //单字符值在进制规则下表示的值
            value += (long) (tempCharValue * Math.pow(scale, str.length() - i - 1));
        }
        return value;
    }

}
