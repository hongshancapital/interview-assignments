package com.domain.urlshortener.service;

import com.domain.urlshortener.config.ConfigProperties;
import com.domain.urlshortener.core.ShortenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 1:33
 */
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    private ShortenManager shortenManager;
    @Autowired
    private ConfigProperties configProperties;

    @Override
    public String createShortUrl(String longUrl) {
        String alias = shortenManager.shorten(longUrl);
        StringBuilder sb = new StringBuilder();
        sb.append(configProperties.getDomain());
        sb.append(alias);
        return sb.toString();
    }

    @Override
    public String getLongUrl(String shortUrl) {
        String longUrl = shortenManager.getUrl(shortUrl.substring(shortUrl.lastIndexOf("/") + 1));
        return longUrl;
    }

}
