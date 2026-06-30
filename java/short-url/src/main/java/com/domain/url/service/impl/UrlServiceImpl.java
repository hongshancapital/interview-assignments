package com.domain.url.service.impl;

import com.domain.url.cache.LRUCache;
import com.domain.url.constant.Constants;
import com.domain.url.exception.ServiceException;
import com.domain.url.helper.AssertHelper;
import com.domain.url.helper.DigitHelper;
import com.domain.url.helper.StringHelper;
import com.domain.url.service.UrlService;
import com.domain.url.service.codes.UrlServiceExceptionCode;
import com.domain.url.web.data.UrlReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短链接服务接口实现
 */
@Service
@Slf4j
public class UrlServiceImpl implements UrlService {

    @Autowired
    private LRUCache lruCache;

    @Override
    public String shorten(UrlReq req) throws ServiceException {
        log.info("[shorten] start... longUrl({})", req.getUrl());

        AssertHelper.isTrue(StringHelper.isUrl(req.getUrl()), UrlServiceExceptionCode.__URL_E_0001__);

        // 1 生成短链接
        // todo 目前实现方式是"长链接一对多短链接"。即同一个长链接，会对应多个不同的短链接。如需实现"同一个长链接多次转换，对应的还是同一个短链接"，我的方案是：空间换空间，即用key-value存储，保存"最近"生成的长对短的一个对应关系。注意是"最近"，也就是说，我并不保存全量的长对短的关系，而只保存最近的。比如采用一小时过期的机制来实现LRU淘汰。
        final String shortUrl = Constants.DOMAIN_SHORT + DigitHelper.generateHex62();

        // 2 存储 短 -> 长链接映射关系
        this.lruCache.put(shortUrl, req.getUrl());

        log.info("[shorten] end. shortUrl({})", shortUrl);
        return shortUrl;
    }

    @Override
    public String original(String shortUrl) throws ServiceException {
        log.info("[original] start... shortUrl({})", shortUrl);

        AssertHelper.isTrue(StringHelper.isUrl(shortUrl), UrlServiceExceptionCode.__URL_E_0002__);

        String longUrl = this.lruCache.get(shortUrl);
        AssertHelper.notBlank(longUrl, UrlServiceExceptionCode.__URL_E_0003__);

        log.info("[original] end.");
        return longUrl;
    }
}
