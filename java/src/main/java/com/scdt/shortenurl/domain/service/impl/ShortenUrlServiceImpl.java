package com.scdt.shortenurl.domain.service.impl;

import com.scdt.shortenurl.domain.service.ShortenUrlService;
import com.scdt.shortenurl.domain.service.ShortenUrlStrategy;
import com.scdt.shortenurl.storage.ShortenUrlStorage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 短链转换服务领域层实现
 * @Author chenlipeng
 * @Date 2022/3/7 2:15 下午
 */
@Service("shortenUrlService")
public class ShortenUrlServiceImpl implements ShortenUrlService {

    @Resource(name = "shortenUrlCache")
    private ShortenUrlStorage shortenUrlStorage;

    @Resource(name = "hashDigestStrategy")
    private ShortenUrlStrategy shortenUrlStrategy;

    @Override
    public String genShortenUrl(String originalUrl) {
        String shortenUrl = shortenUrlStrategy.genShortUrl(originalUrl);
        shortenUrlStorage.save(shortenUrl, originalUrl);
        return shortenUrl;
    }

    @Override
    public String getOriginalUrl(String shortenUrl) {
        return shortenUrlStorage.get(shortenUrl);
    }
}
