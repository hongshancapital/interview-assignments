package com.domain.utils;


import java.util.*;
import java.util.concurrent.*;

/**
 *  短域名生成 工具类
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class ShortUrlGenerator {

    //短域名字符
    private static  String[] CHARS = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                           "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                           "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                           "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                           "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                           "U" , "V" , "W" , "X" , "Y" , "Z" };

    private static Integer size=8;  //短域名长度

    /**
     * 根据URL 生成短地址
     * @param url
     * @return String
     */
    public static String generator(String url){
        String hex = (MD5.MD5Encode( url)); //MD5加密
        String sTempSubString = hex.substring(8, 16); // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
        //  long 型来转换，首位为符号位
        long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 20);
        String outChars = "" ;
        for ( int j = 0; j < size; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            long index = 0x0000003D & lHexLong;
            // 把取得的字符相加
            outChars += CHARS[( int ) index];
            // 每次循环按位右移 3 位
            lHexLong = lHexLong >> 3;
        }
        return outChars;
    }


}
