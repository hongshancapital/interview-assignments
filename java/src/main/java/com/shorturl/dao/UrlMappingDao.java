package com.shorturl.dao;


public interface UrlMappingDao {

    void create(Long intialCode);

    Long insert(Long initialCode, String url);

    void delete(Long code);

    String queryUrl(Long code);
}
