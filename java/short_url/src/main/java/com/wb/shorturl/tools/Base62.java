package com.wb.shorturl.tools;

/**
 * @author bing.wang
 * @date 2021/1/8
 */
public class Base62 {

    public static final String BASE_62_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int BASE = BASE_62_CHAR.length();


    /**
     * 将10进制数字转成62进制
     * @param i the id
     * @return the shortCode
     */
    public static String fromBase10(long i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 0) {
            return "a";
        }
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    /**
     * 将10进制数字转成62进制
     * @param i the id
     * @return the result
     */
    private static long fromBase10(long i, final StringBuilder sb) {
        int rem = (int)(i % BASE);
        sb.append(BASE_62_CHAR.charAt(rem));
        return i / BASE;
    }

}
