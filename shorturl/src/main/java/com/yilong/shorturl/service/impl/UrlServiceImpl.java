package com.yilong.shorturl.service.impl;

import com.yilong.shorturl.common.exception.BusinessException;
import com.yilong.shorturl.dao.UrlDao;
import com.yilong.shorturl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

    @Value("${url.shorter.domain}")
    private String shortUrlDomain;

    @Autowired
    private UrlDao urlDao;

    @Override
    public String saveOriginUrl(String originUrl) {
        String shortCode = urlDao.saveUrl(originUrl);
        if (shortCode == null) {
            throw new BusinessException("Url Storage Has No Space");
        }
        return shortUrlDomain.concat(shortCode);
    }

    @Override
    public String getOriginUrlByShort(String shortUrl) {
        String shortCode = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        if (shortCode.length() > 8) {
            throw new BusinessException("Invalid short url");
        }
        String url = urlDao.getUrlByShortCode(shortCode);
        if (url == null) {
            throw new BusinessException("Invalid short url");
        }
        return url;
    }
}
