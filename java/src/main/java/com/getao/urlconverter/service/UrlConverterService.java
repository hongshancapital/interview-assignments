package com.getao.urlconverter.service;

import com.getao.urlconverter.dto.vo.GetLongUrlVO;
import com.getao.urlconverter.dto.vo.GetShortUrlVO;

public interface UrlConverterService {

    /**
     *
     * @param longUrl
     * @return GetShortUrlVO
     */
    GetShortUrlVO getShortUrl(String longUrl);

    /**
     *
     * @param shortUrl
     * @return GetLongUrlVO
     */
    GetLongUrlVO getLongUrl(String shortUrl);
}
