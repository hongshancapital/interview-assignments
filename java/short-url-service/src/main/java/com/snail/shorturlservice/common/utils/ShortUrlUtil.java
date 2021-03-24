package com.snail.shorturlservice.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class ShortUrlUtil {

    /***
     * 获取字符串哈希值，使用murmur3_32算法，生成32位的哈希值，转成int类型，
     * 如果是小于等于0就取绝对值，然后与Integer.MAX_VALUE相加，转成long类型
     * @param url
     * @return
     */
    public static long getLongHashCode(String url){
        HashCode hashCode = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8);
        int intHashCode = hashCode.asInt();
        long longHashCode = intHashCode <=0 ? Long.valueOf(Integer.MAX_VALUE) + 1 + Math.abs(intHashCode) : intHashCode;
        return longHashCode;
    }
}
