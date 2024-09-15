package com.wwwang.assignment.shortenurl.repository;

/**
 * 域名仓库的统一接口，可以有不同实现
 */
public interface RepoProvider{

    String getLongUrl(String key);

    String getShortUrl(String key);

}
