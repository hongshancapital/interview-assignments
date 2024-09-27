package com.scdt.shortenurl.application.impl;

import com.scdt.shortenurl.application.ShortenUrlBizService;
import com.scdt.shortenurl.domain.service.ShortenUrlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 长短链转换Biz层实现
 * @Author chenlipeng
 * @Date 2022/3/7 2:14 下午
 */
@Service
public class ShortenUrlBizServiceImpl implements ShortenUrlBizService {

    @Resource
    private ShortenUrlService shortenUrlService;

    @Override
    public String genShortenUrl(String originalUrl) {
        return shortenUrlService.genShortenUrl(originalUrl);
    }

    @Override
    public String getOriginalUrl(String shortenUrl) {
        return shortenUrlService.getOriginalUrl(shortenUrl);
    }
}
