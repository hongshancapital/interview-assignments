package com.zm.demo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zm.demo.cache.Cache;
import com.zm.demo.cache.GuavaCache;
import com.zm.demo.sequence.SequenceCreator;
import com.zm.demo.util.Base62Util;

@Service
public class UrlServiceImpl implements UrlService {

    @Value("${url.domain}")
    String domain;

    @Value("${url.cacheMaxSize}")
    int cacheMaxSize;

    @Value("${url.shortUrlLength}")
    int shortUrlLength;

    @Autowired
    SequenceCreator sequenceCreator;

    long maxThreshold;

    //Key:longUrl, value:shortUrlSeq
    Cache<String, Long> longUrlSeqMap;

    //Key:shortUrlSeq, value:longUrl
    Cache<Long, String> shortUrlSeqMap;

    @PostConstruct
    public void init() {
        maxThreshold = (long) Math.pow(62, shortUrlLength);
        longUrlSeqMap = new GuavaCache<>(cacheMaxSize);
        shortUrlSeqMap = new GuavaCache(cacheMaxSize);
    }


    /**
     * 接受长域名信息，返回短域名信息
     * @Param: longUrl
     * @return: java.lang.String
     * @Author: zhaomin
     * @Date: 2021/10/29 17:35
     */
    @Override
    public String shortUrl(String longUrl) {
        Long shortUrlSeq = longUrlSeqMap.get(longUrl);
        if (shortUrlSeq == null) {
            shortUrlSeq = sequenceCreator.createSeq(maxThreshold);

            longUrlSeqMap.put(longUrl, shortUrlSeq);
            shortUrlSeqMap.put(shortUrlSeq, longUrl);
        }

        return domain + Base62Util.base62Encode(shortUrlSeq);
    }

    /**
     * 接受短域名信息，返回长域名信息
     * @Param: shortUrl
     * @return: java.lang.String
     * @Author: zhaomin
     * @Date: 2021/10/29 17:38
     */
    @Override
    public String longUrl(String shortUrl) {
        int pos = shortUrl.indexOf(domain);
        if (pos == -1) {
            return null;
        }

        String shortUrlKey = shortUrl.substring(pos + domain.length());

        long shortUrlSeq = Base62Util.base62Decode(shortUrlKey);
        return shortUrlSeqMap.get(shortUrlSeq);
    }

}
