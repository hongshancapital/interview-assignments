package com.demo.shorturl.util;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.binary.Base64;

/**
 * Description: hash工具类
 * @author : wangjianzhi
 * Create on 2021/9/17
 */
public class HashUtils {

    /**
     * 功能描述: 使用seed生成短域名
     * @param longPath
     * @param seed
     * @return java.lang.String
     * @author wangjianzhi
     * @throws
     * Create on 2021/9/18
     */
    public static String murmur32HashSeed(String longPath, int seed){
        HashFunction hf32Seed = Hashing.murmur3_32(seed);
        return Base64.encodeBase64URLSafeString(hf32Seed.hashString(
                longPath, Charsets.UTF_8).asBytes()).substring(0, 6);
    }
}

