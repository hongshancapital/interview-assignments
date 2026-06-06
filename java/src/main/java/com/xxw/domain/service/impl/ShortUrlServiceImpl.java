package com.xxw.domain.service.impl;


import com.xxw.domain.service.ShortUrlService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Service("shortUrlService")
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final Logger log = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;
    @Value("${short.url.timeout}")
    private Long shortUrlTimeout;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final  String KeyPrefix = "short_url_hash_md5_key_";

    private static final  String shortUrlKeyPrefix = "short_url_hash_key_";

    private static final  String serialNumberKey = "short_url_serial_number";

    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '-', '_'};

    @Override
    public String shortUrl(String url) {

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        final String md5 = DigestUtils.md5Hex(url);
        String shortUrl = operations.get(KeyPrefix + md5);
        if (shortUrl == null){
            synchronized (md5.intern()){
                shortUrl = operations.get(KeyPrefix + md5);
                if (shortUrl == null){
                    Long value = stringRedisTemplate.opsForValue().increment(serialNumberKey, 1);
                    String longString = compressNumber(value);
                    shortUrl = shortUrlPrefix + longString;
                    operations.set(KeyPrefix + md5, shortUrl, shortUrlTimeout, TimeUnit.DAYS);
                    operations.set(shortUrlKeyPrefix + longString, url, shortUrlTimeout, TimeUnit.DAYS);
                }
            }
        }
        return shortUrl;
    }

    public String findOriginalUrl(String shortUrl) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        return operations.get(shortUrlKeyPrefix + shortUrl);
    }

    private static String compressNumber(long number) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }
}
