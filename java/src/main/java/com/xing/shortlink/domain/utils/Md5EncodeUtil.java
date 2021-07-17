package com.xing.shortlink.domain.utils;

import java.security.MessageDigest;

/**
 * md5生成
 *
 * @Author xingzhe
 * @Date 2021/7/17 23:02
 */
public class Md5EncodeUtil {

    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F"};

    /**
     * 字符串md5加密
     *
     * @param inputStr 输入字符串
     * @return 加密后字符串
     */
    public static String md5(String inputStr) {
        return encodeByMD5(inputStr);
    }


    /**
     * 对字符串进行MD5编码
     *
     * @param originString 原始字符串
     * @return md5转换字符串
     */
    private static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md5.digest(originString.getBytes());
                // 将得到的字节数组变成字符串返回
                return byteArrayToHexString(results);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 轮换字节数组为十六进制字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte value : b) {
            sb.append(byteToHexString(value));
        }
        return sb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符
     *
     * @param b 字节
     * @return 十六进制字符
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
