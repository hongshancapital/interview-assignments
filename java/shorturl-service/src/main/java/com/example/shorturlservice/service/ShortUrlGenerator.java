package com.example.shorturlservice.service;

import org.springframework.util.DigestUtils;

/**
 * @Description 短域名生成算法
 * * 1、将长网址md5生成32位签名串，分为4段，每段8个字节；
 * * 2、对这四段循环处理，取8个字节，将他看成16进制串与0x3fffffff(30位1)与操作，即超过30位的忽略处理；
 * * 3、这30位分成6段，每5位的数字作为字母表的索引取得特定字符，依次进行获得6位字符串；
 * * 4、总的md5串可以获得4个6位串；取里面的任意一个就可作为这个长url的短url地址；
 * @Author xingxing.yu
 * @Date 2022/04/15 18:22
 **/
public class ShortUrlGenerator {

    // 要使用生成 URL 的字符
    private static final String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 短域名生成方法
     *
     * @param url
     * @return
     */
    public static String[] shortUrl(String url) {
        //可以自定义生成MD5加密字符串的混合KEY
        String key = "xxyu";

        // 对传入网址进行 MD5 加密
        String hex = DigestUtils.md5DigestAsHex((key + url).getBytes()).toUpperCase();

        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0xFFFFFFFF(32个1) 进行位与运算
            String subString = hex.substring(i * 8, i * 8 + 8);
            long lHexLong = 0xFFFFFFFF & Long.parseLong(subString, 16);
            String outChars = "";
            for (int j = 0; j < 8; j++) {
                // 把得到的值与 0x0000003D（61） 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 4 位
                lHexLong = lHexLong >> 4;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }

}
