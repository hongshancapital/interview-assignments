package com.ttts.urlshortener.service;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.domain.LUrlReq;
import com.ttts.urlshortener.domain.ShortUrlVO;

/**
 * 短链生成
 */
public interface ShortUrlCreateService {

    /**
     * 短链生成
     * @param lurl 长链
     * @return 短链
     */
    ShortUrlVO create(LUrlReq req) throws BusinessException;
}
