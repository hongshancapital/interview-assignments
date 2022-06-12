package com.assignment.util;

import java.util.Date;

/**
 * @Author: shican.sc
 * @Date: 2022/6/12 22:29
 * @see
 */
public class Md5Util {

    public final static String URL_SPILIT= "-";

    public static String getMD5(String source) {
        return getMD5(source.getBytes());
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte tmp[];
            synchronized (Md5Util.class) {
                md.update(source);
                tmp = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            }
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串

        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    /**
     * 生成urlMD5值
     *
     * @param url
     * @return
     */
    public static String getMD5ByUrl(String url) {
        return getMD5(url);
    }

    /**
     * 加盐当前时间戳 生成urlMD5值
     *
     * @param url
     * @return
     */
    public static String random(String url) {
        String md5Str = url + URL_SPILIT + new Date().getTime();
        return getMD5(md5Str);
    }
}
