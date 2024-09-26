package com.example.demo.utils;


import com.google.common.hash.Hashing;

import java.math.BigInteger;

/**
 * @ClassName ShortUrlGenerator
 * @Description TODO
 * @Author gongguanghui
 * @Date 2021/10/14 6:49 PM
 * @Version 1.0
 **/
public class MurmurHashShortUrlGenerator {
//    public static void main(String[] args) {
//        String sLongUrlMD5 = "http://www.baidu.com/1011100";
//        System.out.println(shortenUrl(sLongUrlMD5));
//    }
    private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int BASE = ALPHABETS.length();

    public static String shortenUrl(String url) {
        long murmur32 = Hashing.murmur3_32().hashUnencodedChars(url).padToLong();
        String encoded;
        encoded = encode(murmur32++);
        return encoded;
    }

    private static String encode(long oct) {
        BigInteger octLong = BigInteger.valueOf(oct);
        StringBuilder builder = new StringBuilder(6);
        while (!octLong.equals(BigInteger.ZERO)) {
            BigInteger[] divideAndReminder = octLong.divideAndRemainder(BigInteger.valueOf(BASE));
            builder.append(ALPHABETS.charAt(divideAndReminder[1].intValue()));
            octLong = divideAndReminder[0];
        }
        return builder.reverse().toString();
    }
}
