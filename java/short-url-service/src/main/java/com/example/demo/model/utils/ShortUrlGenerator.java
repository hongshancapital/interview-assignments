package com.example.demo.model.utils;

import cn.hutool.core.lang.hash.MurmurHash;

/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
public class ShortUrlGenerator {

    /**
     * 生成短url
     *
     * @param longUrl 源长url
     * @return shortUrl
     */
    public static String generate(String longUrl) {
        String hash32 = Integer.toUnsignedString(MurmurHash.hash32(longUrl));
        int i = longUrl.hashCode() % (1 << 3);
        return Base62.encode(Long.parseUnsignedLong(hash32));
    }

}