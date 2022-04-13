package com.francis.urltransfer.service.impl;

import com.francis.urltransfer.common.LruLinkedHashMap;
import com.francis.urltransfer.exception.ServerException;
import com.francis.urltransfer.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Francis
 * @Description: 默认短域名服务接口实现
 * @date 2022/1/27 16:58
 */
@Service
@Slf4j
public class DefaultShortUrlServiceImpl implements ShortUrlService {

    /**
     * 缓存原始长域名，key：短域名， value：长域名
     */
    private Map<String, String> urlCache = new LruLinkedHashMap<String, String>(10);

    /**
     * 缓存短域名， key：长域名md5， value：短域名
     */
    private Map<String, String> shortUrlCache = new LruLinkedHashMap<String, String>(10);

    private AtomicLong serialNumber = new AtomicLong(1);

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;

    private static final char[] encodings = {
            '0', '1', '2', '3', '4',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '-',
            '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '_'
    };

    @Override
    public String shorterUrl(String url) {
        log.info("原始长域名：{}", url);
        final String md5 = DigestUtils.md5DigestAsHex(url.getBytes());
        String shortUrl = shortUrlCache.get(md5);
        if (shortUrl == null) {
            synchronized (md5.intern()) {
                shortUrl = shortUrlCache.get(md5);
                if (shortUrl == null) {
                    Long value = serialNumber.getAndIncrement();
                    String longString = compressNumber(value);
                    shortUrl = shortUrlPrefix + longString;
                    shortUrlCache.put(md5, shortUrl);
                    urlCache.put(shortUrl, url);
                }
            }
        }
        return shortUrl;
    }

    @Override
    public String getOriginUrl(String shortUrl) {
        log.info("短域名信息：{}", shortUrl);
        String url = urlCache.get(shortUrl);
        if (StringUtils.isEmpty(url)) {
            log.warn("对应url {},没有找到原链接", shortUrl);
            throw new ServerException(404, "抱歉，原链接已过期销毁");
        }
        return urlCache.get(shortUrl);
    }

    private static String compressNumber(long number) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = encodings[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }
}
