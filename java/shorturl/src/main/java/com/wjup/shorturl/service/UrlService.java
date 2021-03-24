package com.wjup.shorturl.service;

import com.wjup.shorturl.entity.UrlEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by wjup on 2019/9/29 11:34
 */

public interface UrlService {

    UrlEntity findByShortUrlId(String shortUrl);

    void updateShortUrl(String shorlUrlId);

    UrlEntity findByPwd(String viewPwd, String shortUrlId);

    String createShortUrl(String longUrl, String viewPwd, HttpServletRequest request);
}
