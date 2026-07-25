package com.cn.service;

import com.cn.common.Constant;
import com.cn.common.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author wukui
 * @date 2021--12--29
 **/
public class DomainUtil {
    private static final Logger log = LoggerFactory.getLogger(DomainUtil.class);


    public static String[] shortUrl( String url,int shortDomainLength) {
//        log.info("url:"+url);
//        log.info("shortDomainLength:"+shortDomainLength);
        String sMD5Encrypt = MD5Util.md5(url);       // 对url摘要
        String hex = sMD5Encrypt;

        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 加密字符8位一组
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 与 0011 1111 1111 1111 1111 1111 1111 1111 位与运算
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            StringBuilder outChars = new StringBuilder("");
            int num = 32/shortDomainLength -1 ;
            for (int j = 0; j < shortDomainLength; j++) {
                // 与0000 0000 0000 0000 0000 0000 0011 1101位与运算 对应chars 62索引
                long index = 0x0000003D & lHexLong;
                outChars.append(Constant.chars[(int) index]);// 把取得的字符相加
                lHexLong = lHexLong >> num;             // 每次循环按位右移
            }
            resUrl[i] = outChars.toString();                       // 存入输出数组
        }
        return resUrl;
    }
}


