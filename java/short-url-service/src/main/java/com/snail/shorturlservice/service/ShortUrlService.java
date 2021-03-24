package com.snail.shorturlservice.service;

public interface ShortUrlService {

    /***
     * 长url转短url
     * @param url
     * @return 服务域名+短链接key组成的短url
     */
    String shorten(String url);

    /***
     * 用短链接key反查获取原始url
     * @param shortUrlKey
     * @return
     */
    String getLongUrl(String shortUrlKey);
}
