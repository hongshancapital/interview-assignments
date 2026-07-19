package com.diode.interview.domain.repository;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
public interface MyURLRepository {
    boolean saveURL(String longURL, String shortURL, int expireSecs);
    String getShortURL(String longURL);
    String getLongURL(String shortURL);
}
