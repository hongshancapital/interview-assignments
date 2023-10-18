package com.hongshan.link.service.utils;

/**
 * @author heshineng
 * created by 2022/8/9
 * 数字转换成62进制的8位短数据
 * long 的数字最大是 2^32 -1
 * 8位，62进制可以表示的最大数字为：62^8-1=218340105584895L;
 */
public class Base62Utils {

    /**
     * 62进制转换的基础字典
     */
    private final static char[] CHAR_ARRAY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final int BASE_62_SCALE = 62;

    /**
     * 固定8位最长长度
     */
    private static final int LEN = 8;

    /**
     * 8位62进制表示的最大值
     */
    private static final long MAX_BASE62 = 218340105584895L;

    /**
     * 10进制转成62进制
     *
     * @param base10
     * @return
     */
    private static String fromBase10ToBase62(long base10) {
        StringBuilder sb = new StringBuilder();
        while (base10 > BASE_62_SCALE - 1) {
            long remainder = base10 % BASE_62_SCALE;
            sb.append(CHAR_ARRAY[(int) remainder]);
            base10 = base10 / BASE_62_SCALE;
        }
        sb.append(CHAR_ARRAY[(int) base10]);
        return sb.reverse().toString();
    }

    /**
     * 得到62进制转换后的id数据
     *
     * @param base10
     * @return
     */
    public static String getBase62Str(long base10) {
        if (base10 > MAX_BASE62) {
            //大于能表示的最大数据，溢出处理
            throw new NumberFormatException("数字大小溢出");
        }
        String base62 = fromBase10ToBase62(base10);
        if (base62.length() < LEN) {
            int remainder = LEN - base62.length();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < remainder; i++) {
                builder.append(0);
            }
            builder.append(base62);
            base62 = builder.toString();
        }
        return base62;
    }
}
