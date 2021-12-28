/**
 * @(#)UrlUtils.java, 12月 26, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo.utils;

import com.example.demo.constant.DemoErrorEnum;
import com.google.common.base.*;
import com.sun.jndi.toolkit.url.UrlUtil;
import org.apache.commons.collections.CollectionUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.util.*;

/**
 * @author 张三
 */
public class UrlUtils {

    private static final String DEFAULT_TINY_DOMAIN = "http://c.t.cn/";

    private static final String KEY = "demo123456";

    public static final String[] STRS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    private static final Splitter SPLITTER = Splitter.on(DEFAULT_TINY_DOMAIN).omitEmptyStrings().trimResults();

    /**
     * 解析shortUrl得到key
     * @param shortUrl
     * @return
     */
    public static String getKey(String shortUrl) {
        Preconditions.checkState(shortUrl.contains(DEFAULT_TINY_DOMAIN), DemoErrorEnum.PARAM_ERROR.toString());
        List<String> list = SPLITTER.splitToList(shortUrl);
        Preconditions.checkState(CollectionUtils.isNotEmpty(list), DemoErrorEnum.PARAM_ERROR.toString());
        return list.get(0);
    }

    public static String generateShortUrl(String value) {
        return DEFAULT_TINY_DOMAIN + value;
    }


    /**
     * 生成longUrl对应的key
     * @param longUrl
     * @return
     */
    public static String generateKey(String longUrl) {
        try {
            // md5加密
            String md5Str = encodeByMd5(longUrl);

            List<String> resList = new ArrayList<>();
            for(int i = 0; i < 32; i = i + 8){
                String str = md5Str.substring(i, i + 8);
                // 与 0x3FFFFFFF 进行位与运算
                long bitCal = bitCalWith30(str);
                // 获取6位字符串
                String strRes = getResultForLen6(bitCal, STRS);
                resList.add(strRes);
            }
            return resList.get(0);
        } catch (Exception e) {
            throw new RuntimeException(DemoErrorEnum.URL_TRANSFER_ERROR.toString(), e);
        }
    }

    private static String encodeByMd5(String longUrl) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((KEY + longUrl).getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    /**
     * 与 0x3FFFFFFF 进行位与运算
     * @param strPart
     * @return
     */
    private static long bitCalWith30(String strPart) {
        //16进制转换
        long temp = Long.parseLong(strPart, 16);
        //位与运算
        return temp & 0x3FFFFFFF;
    }

    /**
     * 获得6位的字符串
     * @param bitCalResult 第二步的结果
     * @param dict
     * @return
     */
    private static String getResultForLen6(long bitCalResult, String[] dict) {
        StringBuilder sb = new StringBuilder();
        //循环获得每组6位的字符串
        for (int j = 0; j < 6; j++) {
            long index = bitCalResult & 0x0000003D;
            sb.append(dict[(int)index]);
            bitCalResult = bitCalResult >> 5;
        }
        return sb.toString();
    }

}