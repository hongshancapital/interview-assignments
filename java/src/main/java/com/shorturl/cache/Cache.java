package com.shorturl.cache;

public interface Cache {

    void put(String code, String url);

    Long getCode(String url);

    String getUrl(String code);
}
