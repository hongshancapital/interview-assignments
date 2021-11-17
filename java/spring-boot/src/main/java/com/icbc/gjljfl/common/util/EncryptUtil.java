package com.icbc.gjljfl.common.util;

import org.springframework.util.DigestUtils;

/**
 * @Author:
 * @Description: 加密/解密方法
 **/
public class EncryptUtil {

    /**
     * 用户密码加密 TODO
     *
     * @param pwd 用户密码
     * @param key 用户ID
     * @return 加密后用户密码
     */
    public static String pwdEncrypt(String pwd, String key) {
        return salt(pwd+key);
    }

    /**
     * 获取盐值
     *
     * @param key
     * @return
     */
    public static String salt(String key) {
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static void main(String[] args) {
        //3a029f04d76d32e79367c4b3255dda4d
        String salt = pwdEncrypt("123456","3d6124fcbb4c4748884b611f4c7ffd9c");
        System.out.println(salt);
    }

}
