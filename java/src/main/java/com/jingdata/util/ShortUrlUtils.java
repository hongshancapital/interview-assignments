package com.jingdata.util;

import com.jingdata.constant.ConstPools;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 短地址工具类
 *
 * @Author
 * @Date
 */
public class ShortUrlUtils {

    /**
     * md5加密
     * @param longUrl
     * @return 加密字符串
     */
    public static String md5(String longUrl) {
        String encryptResult = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((longUrl).getBytes());
            byte [] digest = md.digest();
            encryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptResult;
    }

    /**
     * 生成短码
     * @param longUrl
     * @return
     */
    public static String generateShortCode(String longUrl) {
        String encrytStr = md5(longUrl);
        char[] result = new char[8];
        for (int i = 0; i < 8; i++) {
            // 把加密字符按照 4 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String subStr = encrytStr.substring(i * 4, i * 4 + 4);
            //将subStr转化为16进制
            long hex = 0x3FFFFFFF & Long.parseLong(subStr, 16);
            // 把得到的值与 62(0x0000003D) 进行位与运算，取得字符数组digits索引下标
            long index = 0x0000003D & hex;
            // 把取得的字符相加
            result[i] = ConstPools.DIGITS[(int) index];
        }
        return new String(result);
    }

}
