package com.polly.shorturl.service.impl;

import com.polly.shorturl.entity.ShortUrl;
import com.polly.shorturl.service.IShortUrlService;
import com.polly.shorturl.tools.ShortUrlHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author polly
 * @date 2022.03.20 10:58:17
 */
@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    @Autowired
    private ShortUrlHandler handler;

    @Override
    public String insertShortUrl(String url) {
        return handler.insert(url);
    }

    @Override
    public String getUrlByShortUrl(String url) {
        ShortUrl shortUrl = handler.getByShortUrl(url);
        if (shortUrl != null) {
            return shortUrl.getUrl();
        }
        return "短域名不存在";
    }
}
