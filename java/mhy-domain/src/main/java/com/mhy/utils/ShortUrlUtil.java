package com.mhy.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 短链接工具类
 */
public class ShortUrlUtil {

    /**
     * 自定义生成 MD5 加密字符传前的混合 KEY
     */
    private static final String KEY = "mhy";

    /**
     * 获取根据长链接生成的6位字符串
     *
     * @param longUrl 长链接
     * @return 6位字符串
     */
    public static String getShortStr(String longUrl) throws NoSuchAlgorithmException {
        //将产生4组6位字符串
        String[] shortUrlArray = getShortStrArratry(longUrl);
        //产成4以内随机数
        Random random = new Random();
        int j = random.nextInt(4);
        //随机获取一个短链接
        return shortUrlArray[j];
    }

    /**
     * 将长链接转换为相关的6位字符串数组
     *
     * @param longUrl 长链接
     * @return 4组6位字符串
     */
    private static String[] getShortStrArratry(String longUrl) throws NoSuchAlgorithmException {
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"

        };
        // 对传入网址进行 MD5 加密
        String hex = Md5Util.md5ByHex(KEY + longUrl);
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            StringBuilder outChars = new StringBuilder();
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars.append(chars[(int) index]);
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars.toString();
        }
        return resUrl;
    }
}
