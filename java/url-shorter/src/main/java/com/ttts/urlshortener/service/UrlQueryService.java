package com.ttts.urlshortener.service;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.domain.ExcelShortUrl;
import com.ttts.urlshortener.domain.ShortUrl;
import java.util.List;
import java.util.Optional;

/**
 * 短链查询
 */
public interface UrlQueryService {

    /**
     * 短链查询
     * @param surl 短链
     * @return 长链
     */
    String queryLurl(String surl) throws BusinessException;

    /**
     * 根据长链查询短链，如果不存在，返回空，否则返回短链
     * @param lurl
     * @return
     */
    Optional<ShortUrl> queryShortUrl(String lurl);

    List<ExcelShortUrl> listAllExcelShortUrl();
}
