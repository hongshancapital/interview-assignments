package com.gc.shorturl.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Author: GC
 * @Date: 2021/3/4
 */
public interface IShortUrlService {

    String convert2ShortUrl(String longUrl);

    String convert2OriginalUrl(String shortUrl);

}
