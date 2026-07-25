package com.weige.shorturl.Service;

public interface DomainService {

    /**
     * @function：根据长域名存储短域名
     */
     String write(String longUrl);


    /**
     * @function 根据短域名获取长域名
     * @param shortUrl
     */
    String read(String shortUrl);
}
