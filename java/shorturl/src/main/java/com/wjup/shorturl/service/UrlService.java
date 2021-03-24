package com.wjup.shorturl.service;

import com.wjup.shorturl.entity.UrlEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: TODO
 * @classname: UrlService
 * @author niuxing@huaxiapawn
 * @date 2021/3/21
*/
public interface UrlService {

    UrlEntity findByShortUrlId(String shortUrl);

    void updateShortUrl(String shorlUrlId);

    UrlEntity findByPwd(String viewPwd, String shortUrlId);

    String createShortUrl(String longUrl, String viewPwd, HttpServletRequest request);
}
