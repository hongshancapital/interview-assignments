package com.ttts.urlshortener.business;

import com.ttts.urlshortener.domain.ShortUrl;
import java.util.List;

public interface ShortUrlBusiness {

    int add(ShortUrl value);

    int deleteById(Long id);

    ShortUrl getBySurl(Long surl);

    List<ShortUrl> getByLurl(String lurl);
    List<ShortUrl> listAllShortUrl();
}
