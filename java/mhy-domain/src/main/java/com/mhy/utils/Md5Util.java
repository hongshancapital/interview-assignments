package com.mhy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5工具类
 */
public class Md5Util {

    /**
     * MD5加密(32位大写)
     *
     * @param content 需要加密的内容
     * @return md5加密后的32位字符串
     */
    public static String md5ByHex(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = content.getBytes();
        md.reset();
        md.update(b);
        byte[] hash = md.digest();
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (byte value : hash) {
            stmp = Integer.toHexString(value & 0xFF);
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }
}
