package com.scdt.china.shorturl.repository;

public interface UrlRepository {

    /**
     * 保存URL
     *
     * @param url URL
     * @return URL ID
     */
    Long save(String url);

    /**
     * 获取URL
     *
     * @param id URL ID
     * @return URL
     */
    String get(Long id);

}
