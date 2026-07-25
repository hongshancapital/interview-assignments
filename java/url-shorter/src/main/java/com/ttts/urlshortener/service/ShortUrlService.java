package com.ttts.urlshortener.service;

import com.ttts.urlshortener.domain.ShortUrl;
import java.util.List;

public interface ShortUrlService {
    ShortUrl add(Long surl, String lurl);

    int deleteById(Long id);

    ShortUrl getBySurl(Long surl);

    List<ShortUrl> getByLurl(String lurl);

    List<ShortUrl> listAllShortUrl();

    void handleExpired();
}
