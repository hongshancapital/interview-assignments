package com.david.urlconverter.utils;

/**
 * 进制转换工具类
 */
public class ConverterUtils {
    private static final char[] DIGITS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();


    private static final long SEED = 62;


    /**
     * 10进制数字转换为62进制字符串
     * @param number
     * @return
     */
    public static String convertNumber(long number){
        return convertNumberToSeed(number,SEED);
    }

    /**
     * 转换为指定进制的字符串
     * @param number
     * @param seed
     * @return
     */
    public static String convertNumberToSeed(long number, long seed){
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0) {
            buf[--charPos] = DIGITS[(int) (number % seed)];
            number /= seed;
        }
        buf[--charPos] = DIGITS[(int) (number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }
}
