package com.example.shortlongurl.web.service;

import com.example.shortlongurl.web.model.ShortLongUrlModel;

/**
 * Url长短链接转换接口类
 */
public interface IUrlGenService {

    ShortLongUrlModel getShortUrl(String longUrl);
    ShortLongUrlModel getLongUrl(String shortUrl);
    boolean isExpire(String key);
}
