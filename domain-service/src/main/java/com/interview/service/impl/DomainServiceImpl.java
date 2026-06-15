package com.interview.service.impl;

import com.interview.common.UrlTranslationError;
import com.interview.exception.InterviewException;
import com.interview.repository.DomainRepository;
import com.interview.service.DomainService;
import com.interview.util.Number62ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: nyacc
 * @Date: 2021/12/17 11:40
 */

@Service
@Slf4j
public class DomainServiceImpl implements DomainService {

    /**
     * 短域名最大长度为8位
     */
    private static final long MAX_CAPACITY=62^8-1;

    private static AtomicLong counter=new AtomicLong();

    @Resource
    private DomainRepository domainRepository;

    @Override
    public String transLongUrlToShortUrl(String longUrl) {
        //简单校验域名是否是合法字符串
        if(!checkLongUrlIsValid(longUrl)){
            throw new InterviewException(UrlTranslationError.URL_INVALID.getCode(), UrlTranslationError.URL_INVALID.getMsg());
        }
        String shortUrl = domainRepository.getShortUrlByLong(longUrl);
        if(shortUrl != null){
            return shortUrl;
        }
        long countNum = counter.incrementAndGet();
        if(countNum>MAX_CAPACITY){
            log.error("生成短域已到资源上线,无法继续生成!!,count容量:{}", counter.longValue());
            throw new InterviewException(UrlTranslationError.COUNTER_FULL.getCode(), UrlTranslationError.COUNTER_FULL.getMsg());
        }
        shortUrl = Number62ConvertUtil.encode(countNum);
        boolean isSuccess= domainRepository.addUrl(shortUrl, longUrl);
        if(!isSuccess){
            counter.decrementAndGet();
        }
        return shortUrl;
    }

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        return domainRepository.getLongUrlByShort(shortUrl);
    }

    /**
     * 简单校验长域名是否合法
     * @param longUrl 长域名
     * @return true合法 false 非法
     */
    public boolean checkLongUrlIsValid(String longUrl){
        if(StringUtils.isEmpty(longUrl)){
            return false;
        }
        return longUrl.startsWith("http") || longUrl.startsWith("https");

    }
}
