package com.hongshan.shorturl.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

/**
 * @author: huachengqiang
 * @date: 2022/3/19
 * @description:
 * @version: 1.0
 */
public class MD5Util {

    private static byte[] salt = "9s*S&ts".getBytes(StandardCharsets.UTF_8);

    /**
     * 计算字符串的md5，返回是byte[]的base64
     *
     * @param str
     * @return {@link String}
     * @throws
     * @date 2022/3/19
     * @author huachengqiang
     */
    public static String md5ToBase64(String str) {
        if (Objects.isNull(str)) {
            return null;
        }
        MessageDigest ctx = DigestUtils.getMd5Digest();
        ctx.update(str.getBytes(StandardCharsets.UTF_8));
        ctx.update(salt);
        return Base64.getEncoder().encodeToString(ctx.digest());
    }
}
