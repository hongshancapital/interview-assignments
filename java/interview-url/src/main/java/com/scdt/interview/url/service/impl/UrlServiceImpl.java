package com.scdt.interview.url.service.impl;

import com.scdt.interview.url.dao.UrlDao;
import com.scdt.interview.url.exception.ShortUrlExhaustedException;
import com.scdt.interview.url.service.UrlService;
import com.scdt.interview.url.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: lijin
 * @date: 2021年10月09日
 */
@RequiredArgsConstructor
@Service
public class UrlServiceImpl implements UrlService {

    private final UrlDao urlDao;

    private final AtomicLong id = new AtomicLong(-1);

    private final static int SHORT_URL_MAX_LEN = 8;

    private final static String SHORT_DOMAIN = "http://dwz.com";

    /**
     * store long url
     * @param longUrl long url，not null
     * @return short url
     * @exception IllegalArgumentException
     * @exception ShortUrlExhaustedException
     */
    @Override
    public String storeLongUrl(String longUrl) {
        Assert.notNull(longUrl, "The long url must not be null");

        boolean exists = urlDao.exists(longUrl);
        if(!exists) {
            String shortUrl = generateShortUrl(longUrl);
            urlDao.saveUrl(longUrl, shortUrl);
        }

        return urlDao.getShortUrl(longUrl);
    }

    /**
     * get long url
     * @param shortUrl shor url，not null
     * @return long url if short url exists else return null
     * @exception IllegalArgumentException
     */
    @Override
    public String getLongUrl(String shortUrl) {
        Assert.notNull(shortUrl, "The long url must not be null");

        return urlDao.getLongUrl(shortUrl);
    }


    /**
     * 生成短链接
     * @param longUrl long url，not null
     * @return short url
     * @exception IllegalArgumentException
     * @exception ShortUrlExhaustedException
     */
    @Override
    public String generateShortUrl(String longUrl){
        Assert.notNull(longUrl, "The long url must not be null");

        long newId = id.addAndGet(1);

        if (newId > Math.pow(62, SHORT_URL_MAX_LEN) - 1) throw new ShortUrlExhaustedException("The short url haven been exhausted");
        return SHORT_DOMAIN + "/" + NumberUtil.dec2Any(newId, 62);
    }

    @Override
    public AtomicLong getId() {
        return id;
    }
}
