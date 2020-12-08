package com.xxx.shortlink.service;

public interface LinkService {
    String save(String link);

    String get(String shortLink);
}
