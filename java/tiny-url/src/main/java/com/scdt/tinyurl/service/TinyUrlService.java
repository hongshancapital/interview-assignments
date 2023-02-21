package com.scdt.tinyurl.service;

import com.scdt.tinyurl.model.UrlEntity;

public interface TinyUrlService {

    String fetchTinyUrl(UrlEntity urlEntity);

    String fetchLongUrl(String tinyUrl);
}
