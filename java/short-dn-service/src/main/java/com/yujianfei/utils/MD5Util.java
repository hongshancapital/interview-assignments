package com.yujianfei.utils;

import java.security.MessageDigest;

/**
 * MD5工具类
 *
 * @author Yujianfei
 */
public class MD5Util {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F"};

    /**
     * 对字符串进行MD5编码
     */
    public static String encodeByMD5(String url) {
        if (url != null) {
            try {

                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] results = md5.digest(url.getBytes());
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
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            resultSb.append(byteToHexString(value));
        }
        return resultSb.toString();
    }

    // 将一个字节转化成十六进制形式的字符串
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String ShortText(String string) {
        String key = "bighero";
        String[] chars = new String[]{
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
                "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        String hex = md5(key + string);
        String outChars = "";
        String subHex = hex.substring(0, 8);
        long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);
        for (int k = 0; k < 6; k++) {
            int index = (int) (Long.valueOf("0000003D", 16) & idx);
            outChars += chars[index];
            idx = idx >> 5;
        }
        return outChars;
    }

    /**
     * 把inputString加密
     */
    public static String md5(String inputStr) {
        return encodeByMD5(inputStr);
    }

}
