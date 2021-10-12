package com.zy.url.service;

import com.zy.url.dto.UrlMapDto;

public interface UrlMapService {
    UrlMapDto createShortUrl(UrlMapDto urlMapDto);
    UrlMapDto restoreUrl(UrlMapDto urlMapDto);
}
