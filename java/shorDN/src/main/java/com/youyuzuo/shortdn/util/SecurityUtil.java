package com.youyuzuo.shortdn.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecurityUtil {

    public static String md5(String ds) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("md5");
        byte[] result = digest.digest(ds.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(result);
    }

    public static String sha1(String ds) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("sha-1");
        byte[] result = digest.digest(ds.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(result);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String aaa = "dsafdsafdsa";
        System.out.println(md5(aaa));
        System.out.println(sha1(aaa));
        System.out.println(NumberUtil.toDigitsString(Long.MAX_VALUE));
        System.out.println(NumberUtil.toDigitsString(IdUtil.nexId()));
    }
}
