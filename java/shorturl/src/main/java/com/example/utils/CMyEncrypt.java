package com.example.utils;

import java.security.MessageDigest;

/**
 * @projectName: shortUrl
 * @package: com.example.utils
 * @className: CMyEncrypt
 * @description: 十六进制下数字到字符MD5编码转换
 * @author: Kai
 * @version: v1.0
 */
public class CMyEncrypt {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };                                              // 十六进制下数字到字符的映射数组

    /**
     * 对字符串进行MD5编码
     *
     * @param originString 编码前字符串
     * @return 编码后字符串
     */
    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");                                       // 创建具有指定算法名称的信息摘要
                byte[] results = md5.digest(originString.getBytes());                                       // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                String result = byteArrayToHexString(results);                                              // 将得到的字节数组变成字符串返回

                return result;
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
     * @return  十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 一个字节转化成十六进制形式的字符串
     *
     * @param b 待转换字节
     * @return  转化成十六进制形式的字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}