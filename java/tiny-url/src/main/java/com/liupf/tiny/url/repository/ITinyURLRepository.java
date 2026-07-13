package com.liupf.tiny.url.repository;

import com.liupf.tiny.url.domain.TinyURL;

public interface ITinyURLRepository {

    /**
     * 保存长链接
     * tip：cache写操作自带Lock
     */
    void saveTinyUrl(TinyURL tinyURL);

    /**
     * 通过Code查询TinyURL
     */
    TinyURL findByCode(String code);


    /**
     * 通过长链接查询TinyURL
     */
    TinyURL findByLongUrl(String longUrl);

}
