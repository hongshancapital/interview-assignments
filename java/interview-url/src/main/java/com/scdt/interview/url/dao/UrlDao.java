package com.scdt.interview.url.dao;

/**
 * @author: lijin
 * @date: 2021年10月09日
 */
public interface UrlDao {

    void saveUrl(String longUrl, String shortUrl);

    boolean exists(String longUrl);

    String getShortUrl(String longUrl);

    String getLongUrl(String shortUrl);
}
