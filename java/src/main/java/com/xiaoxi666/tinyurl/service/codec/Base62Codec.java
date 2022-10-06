package com.xiaoxi666.tinyurl.service.codec;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: base62编码
 */

public class Base62Codec {
    /**
     * 字符集
     */
    private static final String TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 将整形数字进行62进制编码。只支持1~62范围内的数字。
     *
     * @param num
     * @return
     */
    public static String encode(int num) {
        if (num < 1 || num > 62) {
            throw new IllegalArgumentException("62进制编码失败，只支持1~62范围内的数字！传入的数字：" + num);
        }
        return String.valueOf(TABLE.charAt(num - 1));
    }
}
