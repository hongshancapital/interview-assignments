package com.sequoia.interview.service;

/**
 * @author 17612735387@163.com
 * @date 2022/8/13 11:28
 **/
public interface DomainDataCache {

    /**
    * @Description:  get long url by short url
    * @Param: short url
    * @return: longUrl
    * @Author: yhzhang
    * @Date: 2022/8/13
    */
    String getLongUrl(String shortUrl);

    /**
     * @Description:  put key shortUrl value longUrl
     * @Param: shortUrl
     * @Param: longUrl
     * @return: longUrl
     * @Author: yhzhang
     * @Date: 2022/8/13
     */
    boolean putLongUrl(String shortUrl, String longUrl);
}
