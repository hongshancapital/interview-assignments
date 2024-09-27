package com.tinyurl.service;

public interface TinyUrlCodecService {
    String encode(String url);

    String decode(String url);
}
