package com.scdt.aeolus.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.scdt.aeolus.common.CacheUtils;
import com.scdt.aeolus.common.UrlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    @Autowired
    CacheUtils cacheUtils;

    @Value("${config.short-url-length}")
    Integer reservedLength;

    @Value("${config.short-url-prefix}")
    private String prefix;

    /**
     * 长域名转换为短域名
     * @param originalUrl
     * @return
     */
    @Override
    public String saveUrl(String originalUrl) {
        HashFunction function= Hashing.murmur3_32();
        HashCode hashCode = function.hashString(originalUrl, Charset.forName("utf-8"));
        Integer i = Math.abs(hashCode.asInt());
        String shortUrl = UrlConverter.idToStr(Long.valueOf(i), reservedLength);
        cacheUtils.put(shortUrl, originalUrl);
        return prefix + shortUrl;
    }

    /**
     * 短域名还原为长域名
     * @param shortUrl
     * @return
     */
    @Override
    public Optional<String> getUrl(String shortUrl) {
        if (shortUrl.startsWith(prefix)) {
            shortUrl = shortUrl.substring(prefix.length());
        }
        return Optional.ofNullable(cacheUtils.get(shortUrl));
    }
}