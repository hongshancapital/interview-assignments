package com.example.demo.common;

import org.springframework.util.DigestUtils;

/**
 * @Author: dsm
 * @Description: MD5工具类
 * @Date Create in 2021/12/23
 */
public class EncryptionUtils {
    //盐，用于混交md5
    private static final String slat="&%5123***&&%%$$#@";

    public static String md5( String str ) {
        String base=str + "/" + slat;
        String md5=DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
