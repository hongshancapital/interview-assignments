package com.scdt.china.shorturl.service;

public interface ShortUrlRepository {

    /**
     * 添加短链到长链的映射
     * @param shortUrl
     * @param url
     * @return 当短链出现冲突时返回false
     */
    boolean addMapping(String shortUrl, String url);

    String fetchShortUrl(String url);

    String fetchUrl(String shortUrl);

}
