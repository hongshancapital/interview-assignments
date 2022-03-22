package com.wupo.exam.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 域名映射处理类
 * @author wupo
 * @date 2022-03-13
 */
@Component
@Slf4j
public class DomainReflectFacade {

    public static final String NOT_FOUNT_LONG_DOMAIN = "长域名未找到";

    private static final ConcurrentHashMap<String, String> REFLECT_MAP = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, String> ANTI_REFLECT_MAP = new ConcurrentHashMap<>();

    private static final AtomicLong COUNT = new AtomicLong(1000000L);

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 转64进制的编码
     */
    private static final char[] code = {
            'a', '1', 'A', 'b', '2', 'B', 'c', '3', 'C', 'd', '4', 'D',
            'e', '5', 'E', 'f', '6', 'F', 'g', '7', 'G', 'h', '8', 'H',
            'i', '9', 'I', 'j', '0', 'J', 'k', '-', 'K', 'l', 'Z', 'L',
            'm', 'z', 'M', 'n', 'Y', 'N', 'o', 'y', 'O', 'p', 'X', 'P',
            'q', 'x', 'Q', 'r', 'W', 'R', 's', 'w', 'S', 't', 'V', 'T',
            'u', 'v', 'U', '.'
    };

    public String saveLongDomain(String url){
        if(ANTI_REFLECT_MAP.containsKey(url)){
            return ANTI_REFLECT_MAP.get(url);
        }
        long value = COUNT.getAndIncrement();
        //long value = COUNT.addAndGet(RANDOM.nextInt(10));
        String shortUrl = compressLong(value);
        REFLECT_MAP.put(shortUrl, url);
        ANTI_REFLECT_MAP.put(url, shortUrl);
        return shortUrl;
    }

    public String getLongDomain(String url){
        if(REFLECT_MAP.containsKey(url)){
            return REFLECT_MAP.get(url);
        }
        return NOT_FOUNT_LONG_DOMAIN;
    }

    private String compressLong(long number){
        char[] buffer = new char[8];
        int p = 0;
        int radix = (1 << 6) - 1;
        while(number != 0L && p < 8){
            buffer[p++] = code[(int)(number & radix)];
            number >>>= 6;
        }
        return new String(buffer, 0, p);
    }

}
