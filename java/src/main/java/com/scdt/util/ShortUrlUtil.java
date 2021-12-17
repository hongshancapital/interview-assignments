package com.scdt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ShortUrlUtil
 *
 * @Author: lenovo
 * @since: 2021-12-16 14:05
 */
public class ShortUrlUtil {

    // 要使用生成 URL 的字符
    private static final String[] URL_CHARS = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
            "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
            "u" , "v" , "w" , "x" , "y" , "z" ,"A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
            "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
            "U" , "V" , "W" , "X" , "Y" , "Z", "0" , "1" , "2" , "3" , "4" , "5" ,
            "6" , "7" , "8" , "9"
    };
    /**
     * 混合key
     */
    private static final String key = "url" ;
    private static  MessageDigest md5 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

   public static String getMd5HexStr(String url){
        assert url!=null && !url.equals("");
        byte[] md5Bytes =md5.digest(url.getBytes());
       return parseByte2HexStr(md5Bytes);
    }
    /**
     * @description 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 获取短URL 取数数组第一个元素
     * @param url
     * @return 返回短url
     */
    public static String shortUrl(String url) {
        return shortUrls(url)[0];
    }

    public static String[] shortUrls(String url) {
        // 对传入url进行 MD5 加密
        String hex = ShortUrlUtil.getMd5HexStr(key + url);
        String[] resUrl = new String[4];
        for ( int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
            StringBuilder outChars = new StringBuilder();
            for ( int j = 0; j < 8; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 字符串拼接
                outChars.append(URL_CHARS[(int) index]);
                // 每次循环按位右移 4位
                lHexLong = lHexLong >> 4;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars.toString();
        }
        return resUrl;
    }
}
