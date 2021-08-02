package com.sequoia.shorturl.util;

import java.util.Random;

/**
 * @auther: usr1999
 * @create: 2021/7/28
 */
public class UrlUtil {

    private static String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String genShortUrl() {
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            sb.append(BASE62.charAt(rand.nextInt(62)));
        }
        return sb.toString();
    }


}
