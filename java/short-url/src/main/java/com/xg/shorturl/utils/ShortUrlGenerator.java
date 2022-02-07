package com.xg.shorturl.utils;

/**
 * 生成8位短链接
 * @author xionggen
 */
public class ShortUrlGenerator {

    private static final String[] chars = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };
    /**
     * md5加密混合key
     */
    private static final String key = "123456";

    public static String generate(String url) {
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = (Encrypt.md5(key + url));
        // 取加密字符串前10位
        String sTempSubString = sMD5EncryptResult.substring(0, 10);
        // 转为十六进制与0xFFFFFFFF进行位运算，保留40个bit位
        long lHexLong = 0xFFFFFFFF & Long.parseLong(sTempSubString, 16);
        StringBuilder stringBuilder = new StringBuilder();
        // 循环获得8位的字符，每5个bit位对应一个字符
        for (int j = 0; j < 8; j++) {
            // 把得到的值与 0x0000003D(61)进行位与运算，取得字符数组 chars(62个) 索引
            long index = 0x0000003D & lHexLong;
            // 把取得的字符相加
            stringBuilder.append(chars[(int) index]);
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        // 把字符串存入对应索引的输出数组
        return stringBuilder.toString();
    }
}
