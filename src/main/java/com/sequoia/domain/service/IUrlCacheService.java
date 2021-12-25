package com.sequoia.domain.service;

public interface IUrlCacheService {
    /**
     * 緩存域名
     *
     * @param id  域名id
     * @param url 長域名(Base64編碼)
     */
    void put(long id, String url);

    /**
     * 查詢厂域名
     *
     * @param id 域名id
     * @return 長域名(Base64編碼)
     */
    String get(long id);
}
