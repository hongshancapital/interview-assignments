package com.ascmix.surl.service;

public interface LinkService {
    String shorten(String url);

    String get(String key);

    boolean delete(String key);
}
