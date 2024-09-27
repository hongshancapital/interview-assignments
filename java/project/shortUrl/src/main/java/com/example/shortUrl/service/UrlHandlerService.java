package com.example.shortUrl.service;

import com.example.shortUrl.pojo.Result;

/**
 * @Author HOPE
 * @Description url处理接口类
 * @Date 2022/4/28 20:00
 */
public interface UrlHandlerService {
    public Result<String> getShortUrl(String longUrl);
    public Result<String> getLongUrl(String shortUrl);
}
