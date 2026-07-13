package com.liupf.tiny.url.service;

public interface ITinyURLService {

    /**
     * 保存LongUrl
     *
     * @return 短链接
     */
    String saveLongUrl(String longUrl);

    /**
     * 查询LongUrl
     *
     * @return 长链接
     */
    String getLongUrl(String shortUrl);

    /**
     * 查询LongUrl
     *
     * @return 长链接
     */
    String getLongUrlByCode(String code);


}
