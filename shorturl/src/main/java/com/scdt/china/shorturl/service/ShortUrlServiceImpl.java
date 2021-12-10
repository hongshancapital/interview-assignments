package com.scdt.china.shorturl.service;

import com.scdt.china.shorturl.service.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlGenerator shortUrlGenerator;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Value("${conflict.retryCount:3}")
    private int retryCount;

    @Override
    public String generateShortUrl(String url) {

        checkUrl(url);

        String existShortUrl = shortUrlRepository.fetchShortUrl(url);
        if (StringUtils.isNotBlank(existShortUrl)) {
            return existShortUrl;
        }

        int count = 1;
        String shortUrl = shortUrlGenerator.generateShortUrl(url);
        while (!shortUrlRepository.addMapping(shortUrl,url) && count < retryCount) {
            count++;
            shortUrl = shortUrlGenerator.generateShortUrlWhenConflicted(url, shortUrl);
        }

        if (count == retryCount) {
            throw new BizException("system is busy, please try again later");
        }

        return shortUrl;
    }

    private void checkUrl(String url) {

        if (StringUtils.isBlank(url)) {
            throw new BizException("url can not be empty");
        }

        if (!UrlValidator.validate(url)) {
            throw new BizException("url is incorrect");
        }
    }

    @Override
    public String fetchUrl(String shortUrl) {

        if (StringUtils.isBlank(shortUrl)) {
            throw new BizException("shortUrl can not be empty");
        }

        return shortUrlRepository.fetchUrl(shortUrl);
    }
}
