package com.example.sequoiahomework.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author Irvin
 * @description 长链接转短链接的工具类
 * @date 2021/10/9 20:16
 */
public class UrlUtils {

    private static final Pattern URL_PATTERN = Pattern
            .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~/])+$");

    /**
     * 检验是否是网址
     *
     * @param str 字符
     * @return boolean
     * @author Irvin
     * @date 2021/10/10
     */
    public static boolean isUrl(String str) {
        return URL_PATTERN.matcher(str).matches();
    }


    /**
     * 根据连接获取短链接
     *
     * @param longUrl 长链接
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    public static String longToShort(String longUrl) {
        String[] urls = shortUrl(longUrl);
        Random random = new Random();
        int num = random.nextInt(4);
        return urls[num];
    }


    /**
     * 根据连接获取短链接
     *
     * @param url 链接
     * @return java.lang.String[]
     * @author Irvin
     * @date 2021/10/9
     */
    private static String[] shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "Irvin";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };

        // 对传入网址进行 MD5 加密
        String hex = DigestUtils.md5Hex(key + url);

        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
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
