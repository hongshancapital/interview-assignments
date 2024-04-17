package com.scdt.china.shorturl.util;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 短域名生成器
 *
 * @author：costa
 * @date：Created in 2022/4/14 15:30
 */
public class ShortUrlGenerator {

    //62字符乱序数组
    private static final char[] chars = {'0', 'G', '2', '3', '4', '5', 'R', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', '1', 'H', 'I', 'J', 'V', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', '6', 'S', 'T', 'U', 'K', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成短域名
     */
    public static String shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "scdt-interview";
        // 对传入网址进行 MD5 加密
        String hex = SecureUtil.md5(key + url);
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
        //随机取一个返回
        int index = RandomUtil.randomInt(4);
        return resUrl[index];
    }

}


