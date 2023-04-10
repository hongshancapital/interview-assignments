package com.example.baiyang.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/15
 * @description: 短域名生成工具类
 */
@Slf4j
public class ShortUrlGenerateUtil {

    //自定义生成MD5加密字符传前的混合KEY
    private final static String CUSTOM_KEY = "baiyang";

    //要使用生成URL的字符
    private final static String[] CHARS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    /**
     * 获取生成的短域名其中的一个
     *
     * @param url
     * @param digest
     * @return
     */
    public static String getRandomShortUrl(String url, String digest) {
        String[] shortUrls = generateShortUrls(url, digest);
        if (null != shortUrls && shortUrls.length > 0) {
            return shortUrls[0];
        }

        return StringUtils.EMPTY;

    }

    /**
     * 传入长域名，根据摘要算法生成4个短域名
     *
     * @param url
     * @param digestParam
     * @return
     */
    public static String[] generateShortUrls(String url, String digestParam) {

        //对传入网址进行MD5加密
        String sMD5EncryptResult = "";

        try {
            MessageDigest md = MessageDigest.getInstance(digestParam);
            md.update((CUSTOM_KEY + url).getBytes());
            byte[] digest = md.digest();
            sMD5EncryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (Exception e) {
            log.error("ShortUrlGenerateUtil.shortUrl encounters an exception", e.getMessage(), e);
            return null;
        }

        String[] resUrl = new String[4];

        //生成4组短域名
        for (int i = 0; i < 4; i++) {
            String sTempSubString = sMD5EncryptResult.substring(i * 8, i * 8 + 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";

            for (int j = 0; j < 6; j++) {
                long index = 0x0000003D & lHexLong;

                outChars += CHARS[(int) index];
                lHexLong = lHexLong >> 5;
            }

            resUrl[i] = outChars;
        }

        return resUrl;
    }

}
