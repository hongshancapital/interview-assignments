package com.example.homework.service.impl;

import com.example.homework.service.UrlService;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: Karl
 * @date: 2022/4/27
 */
@Service
public class UrlServiceImpl implements UrlService {

    static final char[] DIGITS =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    private final AtomicLong sequence = new AtomicLong(0);

    private final BiMap<String, String> biMap = Maps.synchronizedBiMap(HashBiMap.create());

    /**
     * 获取长域名url
     *
     * @param shortUrl 短域名url
     * @return 长域名url
     */
    @Override
    public String getLongUrl(String shortUrl) {
        return biMap.get(shortUrl);
    }

    /**
     * 保存长域名url
     *
     * @param longUrl 长域名url
     * @return 短域名url
     */
    @Override
    public String saveLongUrl(String longUrl) {
        if (Objects.nonNull(biMap.inverse().get(longUrl))) {
            return biMap.inverse().get(longUrl);
        }
        long myseq = sequence.incrementAndGet();
        biMap.put(to62RadixString(myseq), longUrl);
        return to62RadixString(myseq);
    }


    /**
     * 62进制转换
     *
     * @param seq 十进制数
     * @return 62进制字符串
     */
    private String to62RadixString(long seq) {
        StringBuilder sBuilder = new StringBuilder();
        do {
            int remainder = (int) (seq % 62);
            sBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
        } while (seq != 0);
        return sBuilder.toString();
    }
}