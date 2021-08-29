package com.example.demo.util;

import com.example.demo.common.Constants;

import java.security.MessageDigest;

public class Md5Util {
    /**
     * MD5加密(32位大写)
     *
     * @param src 原始字符串
     * @return 加密之后的字符串
     */
    public static String md5ByHex(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = src.getBytes();
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuffer buffer = new StringBuffer();
            String stmp = "";
            for (int i = Constants.INDEX_0; i < hash.length; i++) {
                stmp = Integer.toHexString(hash[i] & Constants.LONG_0XFF);
                if (stmp.length() == Constants.INDEX_1) {
                    buffer.append("0" + stmp);
                } else {
                    buffer.append(stmp);
                }
            }
            return buffer.toString().toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }

}
