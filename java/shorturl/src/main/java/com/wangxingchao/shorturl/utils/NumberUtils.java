package com.wangxingchao.shorturl.utils;

import com.wangxingchao.shorturl.exception.MaxLengthException;

/**
 * 数字工具类
 */
public class NumberUtils {

    // 顺序初始化进制字符, 这是正常的进制转换
    // 可以通过打乱这个顺序实现短码的简单加密，解决安全隐患
    private final static String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 将10进制数字转换为指定进制
     * @param number 数字
     * @param scale 进制
     * @param length 限制长度
     * @return 相应进制的字符串
     */
    public static String converter(int number, int scale, int length) {
        StringBuilder sb = new StringBuilder();
        // 对 scale 进行求余，然后将余数追加至 sb 中
        while (number > scale - 1) {
            sb.append(CHARS.charAt(number % scale));
            number = number / scale;
        }
        sb.append(CHARS.charAt(number));
        // 当生成短码大于指定
        if (sb.toString().length() > length) {
            throw new MaxLengthException("进制转换超出设定长度，请检查自增键！");
        }
        // 由于是从末位开始追加的，所以补全直接追加CHARS的第一位就可以了
        while (sb.toString().length() < length) {
            sb.append(CHARS.charAt(0));
        }
        // 只有长度限制 所以没必要翻转
//        String value = sb.reverse().toString();
        return sb.toString();
    }

}
