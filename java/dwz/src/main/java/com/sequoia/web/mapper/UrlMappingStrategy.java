package com.sequoia.web.mapper;

public interface UrlMappingStrategy {
    public String put(String key, String value);
    public String get(String key);
    public int size();
}
