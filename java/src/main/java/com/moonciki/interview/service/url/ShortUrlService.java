package com.moonciki.interview.service.url;

import com.moonciki.interview.vo.url.ShortUrlVo;

public interface ShortUrlService {

    /**
     * 短网址
     */
    ShortUrlVo createShort(String fullUrl);

    ShortUrlVo getFullUrl(String shortUrl);

}
