package com.sequoia.shortdomain.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 散列算法返回的128bit的编码，HEX编码后的长度为32Byte
 * @author codefan
 *
 */
@SuppressWarnings("unused")

public class Md5Encoder {
    private Md5Encoder() {
        throw new IllegalAccessError("Utility class");
    }

    protected static final Logger logger = LoggerFactory.getLogger(Md5Encoder.class);

    public static byte[] rawEncode(byte[] data){
        MessageDigest MD5;
        try {
            MD5 = MessageDigest.getInstance("MD5");
            MD5.update(data, 0, data.length);
            return MD5.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(),e);//e.printStackTrace();
            return null;
        }
    }

    public static String encode(byte[] data){
        byte [] md5Code = rawEncode(data);
        if(md5Code!=null){
            return new String(Hex.encodeHex(md5Code));
        } else {
            return null;
        }
    }

    public static String encode(String data){
        try {
            return encode(data.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);//e.printStackTrace();
            return null;
        }
    }

    /**
     * 将md5 编码进行base64编码，去掉最后的两个==，16为的md5码base64后最后两位肯定是==
     * @param data 需要编码的 数据
     * @return 将md5 编码进行base64编码，去掉最后的两个==
     */
    public static String encodeBase64(byte[] data){
        byte [] md5Code = rawEncode(data);
        if(md5Code != null){
            return new String(Base64.encodeBase64(md5Code),0,22);
        } else {
            return null;
        }
    }

    public static String encodeBase64(String data){
        try {
            return encodeBase64(data.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);//e.printStackTrace();
            return null;
        }
    }
    /**
     * encoding password for spring security
     * 目前框架中的密码都是这样加密的
     * @param data  密文
     * @param salt  盐
     * @return 散列值
     */
    public static String encodePasswordAsSpringSecurity(String data,String salt){
        return encode(data + "{" + salt + "}");
    }


    /**
     * encoding password for spring JA-SIG Cas
     * @param data  密文
     * @param salt  盐
     * @param iterations 迭代次数
     * @return 散列值
     */
    public static String encodePasswordAsJasigCas(String data,String salt, int iterations){
        MessageDigest MD5;
        try {
            MD5 = MessageDigest.getInstance("MD5");
            byte[] saltBytes = salt.getBytes("utf8");
            MD5.update(saltBytes, 0, saltBytes.length);
            byte[] hashedBytes = MD5.digest(data.getBytes("utf8"));
            for(int i=0;i<iterations-1;i++)
                hashedBytes = MD5.digest(hashedBytes);
            return new String(Hex.encodeHex(hashedBytes));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);//e.printStackTrace();
            return null;
        }
    }

    /**
     * 先腾框架默认的密码算法
     * @param data  密文
     * @param salt  盐
     * @return 散列值
     */
    public static String encodePassword(String data,String salt){
        return encodePasswordAsSpringSecurity(data , salt);
    }

    /**
     * 先腾框架双重加密算法： 客户端 用md5将密码加密一下传输到后台，后台将密码再用salt加密一下放入数据库中
     *         这个算法可以用于后台设置密码时使用，正常验证和以前一样。
     * @param data  密文
     * @param salt  盐
     * @return 散列值
     */
    public static String encodePasswordWithDoubleMd5(String data,String salt){
        return encodePasswordAsSpringSecurity(
                encode(data) , salt);
    }
}
