package com.cn.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @author wukui
 * @date 2021--12--29
 **/
public class MD5Util {
    private static final Logger log = LoggerFactory.getLogger(MD5Util.class);


    /**
     * 把inputString加密
     */
    public static String md5(String inputStr) {
        return encodeByMD5(inputStr);
    }

//    /**
//     * 验证输入的密码是否正确
//     *
//     * @param password    真正的密码（加密后的真密码）
//     * @param inputString 输入的字符串
//     * @return 验证结果，boolean类型
//     */
//    public static boolean authenticatePassword(String password, String inputString) {
//        if (password.equals(encodeByMD5(inputString))) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * 使用MD5对字符串进行摘要
     */
    public static String encodeByMD5(String str) {
        if (str != null) {
            try {
                // 创建具有指定MD5算法
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                // 计算摘要
                byte[] results = md5.digest(str.getBytes());
                String md5Code = byteArrayToHexString(results);
                return md5Code;
            } catch (Exception e) {
                e.printStackTrace();
                log.error("使用MD5对字符串进行摘要异常:"+e);
                return null;
            }
        }
        return null;
    }

    /**
     * 轮换字节数组为十六进制字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    // 将一个字节转化成十六进制形式的字符串
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return Constant.hexDigits[d1] + Constant.hexDigits[d2];
    }

}
