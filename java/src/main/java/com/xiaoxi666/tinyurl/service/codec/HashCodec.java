package com.xiaoxi666.tinyurl.service.codec;

import org.hashids.Hashids;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: hashids编解码
 */

public class HashCodec {
    private static final String SALT = "salt of xiaoxi666";
    private static final Hashids HASHIDS = new Hashids(SALT);

    /**
     * 将id编码为base62字符串
     * @param src
     * @return
     */
    public static String encode(long src) {
        return HASHIDS.encode(src);
    }

    /**
     * 将base62字符串编码为id
     * @param dest
     * @return
     */
    public static long decode(String dest) {
        return HASHIDS.decode(dest)[0];
    }
}
