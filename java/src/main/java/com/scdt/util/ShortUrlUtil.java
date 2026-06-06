package com.scdt.util;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class ShortUrlUtil {

    private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final int SHORT_MAX_LENGTH = 8;

    private static final int HEX = encodes.length - 1;

    private static final int BIGINT = 0x3FFFFFFF;

    public static String encode(String content) throws UnsupportedEncodingException {
        String[] shortUrls = shortUrl(content);
        Random random = new Random();
        int i = random.nextInt(shortUrls.length);
        return shortUrls[i];
    }

    public static String[] shortUrl(String content) throws UnsupportedEncodingException {
        //md5加密内容
        String md5Str = DigestUtils.md5DigestAsHex(content.getBytes(DEFAULT_ENCODING));
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            //加密字符串截取
            String sTempSubString = md5Str.substring(i * 8, (i + 1) * 8);
            // 截取的字符串转数字
            long lHexLong = BIGINT & Long.parseLong(sTempSubString, 16);
            StringBuilder outChars = new StringBuilder();
            for (int j = 0; j < SHORT_MAX_LENGTH; j++) {
                //计算当前索引位置
                long index = lHexLong & HEX;
                outChars.append(encodes[(int) index]);
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars.toString();
        }
        return resUrl;
    }

}
