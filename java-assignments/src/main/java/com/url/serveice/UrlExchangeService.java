package com.url.serveice;

import org.springframework.stereotype.Service;

public interface UrlExchangeService {

    /**
     * 根据长连接，获取短链接
     * @param longUrl
     * @return
     */
    String getShort(String longUrl);

    /**
     * 根据短链接获取长连接
     * @param shortUrl
     * @return
     */
    String getLong(String shortUrl);

}
