package com.wwwang.assignment.shortenurl.service.impl;

import com.wwwang.assignment.shortenurl.exception.BizException;
import com.wwwang.assignment.shortenurl.repository.RepoProvider;
import com.wwwang.assignment.shortenurl.service.IShortenUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements IShortenUrlService {


    @Autowired
    private RepoProvider repoProvider;

    @Override
    public String getShortUrl(String longUrl) {
        if(StringUtils.isBlank(longUrl)){
            throw new BizException("非法请求参数");
        }
        return repoProvider.getShortUrl(longUrl);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        if(StringUtils.isBlank(shortUrl)){
            throw new BizException("非法请求参数");
        }
        return repoProvider.getLongUrl(shortUrl);
    }
}
