package com.sequoia.shorturl.common.server;

import cn.hutool.core.lang.hash.MurmurHash;
import cn.hutool.core.util.HashUtil;
import com.sequoia.shorturl.common.util.Base62;

/**
 * @Author: xxx
 * @Description: 短链发号器
 * @Date: 2022/1/4 22:11
 * @Version: 1.0.0
 */
public class ShortUrlGenerator {

    /**
     * 生成短url
     *
     * @param longUrl 源 长url
     * @return shortUrl
     */
    public static String generate(String longUrl) {
        String hash32 = Integer.toUnsignedString(MurmurHash.hash32(longUrl));
        int i = longUrl.hashCode() % (1 << 3);
        return Base62.encode(Long.parseUnsignedLong(hash32));
    }

}
