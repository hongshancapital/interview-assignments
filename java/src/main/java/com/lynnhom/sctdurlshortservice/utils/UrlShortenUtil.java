package com.lynnhom.sctdurlshortservice.utils;

import com.google.common.hash.Hashing;
import com.lynnhom.sctdurlshortservice.common.enums.RespCodeEnum;
import com.lynnhom.sctdurlshortservice.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @description: 短链接生成工具
 * @author: Lynnhom
 * @create: 2021-10-29 17:20
 **/

@Slf4j
public class UrlShortenUtil {

    private UrlShortenUtil() {}

    /**
     * 长链接生成短链接
     * @param originalUrl
     * @return
     */
    public static String generate (String originalUrl) {
        String shortUrl;
        try {
            // 对长链接地址进行Hash运算
            Integer hashUrlInt = Hashing.murmur3_32()
                    .hashString(originalUrl, StandardCharsets.UTF_8)
                    .asInt();
            // 转换为正数 范围 0 ~ 4294967295
            Long hashUrl = hashUrlInt >= 0 ?
                    hashUrlInt.longValue() : Long.valueOf(Integer.MAX_VALUE) - hashUrlInt;
            // 转换为6位长度的BASE62
            shortUrl = Base62Util.encode(hashUrl);
            // 补全长度
            StringBuilder shortUrlSb = new StringBuilder(shortUrl);
            while(shortUrlSb.length() < 6) {
                shortUrlSb.insert(0, Base62Util.ALPHABET.charAt(0));
            }
            shortUrl = shortUrlSb.toString();
        } catch (Exception e) {
            log.error("generate, short url generate exception, originalUrl={}, e", originalUrl, e);
            throw new BizException(RespCodeEnum.URL_SHORTEN_EXCEPTION);
        }
        return shortUrl;
    }
}
