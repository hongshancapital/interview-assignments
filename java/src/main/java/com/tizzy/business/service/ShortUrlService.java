package com.tizzy.business.service;

import com.tizzy.business.exception.EntityNotFoundException;
import com.tizzy.business.repository.DataManager;
import com.tizzy.business.util.ConversionUtil;
import com.tizzy.business.util.Counter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService {

    private static final int urlTimeLimit = 10 * 60 * 1000;

    public String convertToShortUrl(String longUrl, long expiresTime) {
        long timeStamp = expiresTime;
        if (expiresTime <= 0) {
            timeStamp = System.currentTimeMillis() + urlTimeLimit;
        }

        long tempCode = DataManager.getInstance().getFromCache(longUrl);
        if (tempCode > 0) {
            return getShortUrlAndRefreshTime(tempCode, longUrl, timeStamp);
        }

        long urlCode = Counter.getOneCode();
        return getShortUrlAndRefreshTime(urlCode, longUrl, timeStamp);
    }

    public String getOriginalUrl(String shortUrl) throws EntityNotFoundException {
        long urlCode = ConversionUtil.decode(shortUrl);
        String longUrl = DataManager.getInstance().get(urlCode);

        if (StringUtils.isEmpty(longUrl)) {
            throw new EntityNotFoundException("original link not found!");
        }

        return longUrl;
    }

    private String getShortUrlAndRefreshTime(long urlCode, String longUrl, long expiresTime) {
        String shortKey = ConversionUtil.encode(urlCode);
        DataManager.getInstance().put(urlCode, longUrl, expiresTime);
        return shortKey;
    }

}
