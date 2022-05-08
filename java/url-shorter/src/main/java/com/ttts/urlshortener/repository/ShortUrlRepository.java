package com.ttts.urlshortener.repository;

import com.ttts.urlshortener.domain.ShortUrl;
import java.util.List;

public interface ShortUrlRepository {
    int add(ShortUrl value);

    int deleteById(Long id);

    ShortUrl getBySurl(Long surl);

    List<ShortUrl> getByLurl(String lurl);

    List<ShortUrl> listAllShortUrl();
}
