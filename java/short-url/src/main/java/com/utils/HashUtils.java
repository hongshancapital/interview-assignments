package com.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.binary.Base64;

/**
 * Description: 工具类
 * @author : lingjiangwei
 * Create on 2021/10/10
 */
public class HashUtils {
    /**
     * 功能描述: 生成短域名
     * @author liangjiangwei
     * Create on 2021/10/10
     */
    public static String murmur32HashSeed(String longUrl, int randomNumber ){
        HashFunction seed = Hashing.murmur3_32(randomNumber);
        return Base64.encodeBase64URLSafeString(seed.hashString(longUrl, Charsets.UTF_8).asBytes()).substring(0, 6);
    }
}
