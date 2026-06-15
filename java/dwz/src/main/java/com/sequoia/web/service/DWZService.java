package com.sequoia.web.service;

import com.sequoia.web.mapper.UrlMapper;

public interface DWZService {

    public String getLongUrlByShortUrl(String shortURL);

    public String saveShortUrlByLongUrl(String originalURL);

    public void setUrlMapper(UrlMapper urlMapper);
}
