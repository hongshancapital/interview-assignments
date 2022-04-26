package com.scc.base.service;

/**
 * @author renyunyi
 * @date 2022/4/24 2:35 PM
 * @description short url service interface
 **/
public interface UrlService {

    /**
     * input long url(eg:  http://www.scc.com/hello/this/is/a/long/url/dsgalgteasdskqWld)
     * and return a short url (eg: http://www.scc.com/hejR9jci) the long url mapped
     *
     * @param longUrl long url message
     * @return short url message
     */
    String getShortUrlFrom(String longUrl);

    /**
     * input short url (eg: http://www.scc.com/hejR9jci),
     * and return a long url(eg:  http://www.scc.com/hello/this/is/a/long/url/dsgalgteasdskqWld) the short url mapped
     *
     * @param shortUrl short url message
     * @return long url message
     */
    String getLongUrlFrom(String shortUrl);
}
