package com.example.shorturl.service.core;

import com.example.shorturl.service.dto.ShortUrlDto;

/**
 * @author yyp
 * @date 2022/1/16 11:07
 */
public interface IShortUrlService {
    /**
     * 传入长链接，获取短链接
     * @param longUrl
     * @return
     */
    ShortUrlDto getShortUrl(String longUrl);

    /**
     * 传入短链接，获取长链接
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);
}
