package com.scdt.china.shorturl.service;

public interface ShortUrlGenerator {

    String generateShortUrl(String url);

    /**
     * 冲突时重新生成方法
     * @param url
     * @param conflictedShortUrl
     * @return
     */
    String generateShortUrlWhenConflicted(String url, String conflictedShortUrl);

}
