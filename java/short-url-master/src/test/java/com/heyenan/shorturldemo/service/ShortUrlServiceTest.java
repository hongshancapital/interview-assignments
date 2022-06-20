package com.heyenan.shorturldemo.service;

import com.heyenan.shorturldemo.common.request.ShortUrlRequest;
import com.heyenan.shorturldemo.datacache.ShortUrlFactory;
import com.heyenan.shorturldemo.strategy.ExecStrategy;
import com.heyenan.shorturldemo.strategy.HashStrategy;
import com.heyenan.shorturldemo.strategy.IdGrowStrategy;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class ShortUrlServiceTest {
    @Resource
    private ShortUrlService shortUrlService;

    @Resource
    private ShortUrlFactory urlDataCache;

    private ShortUrlRequest request;

    @BeforeEach
    public void init() {
        request = new ShortUrlRequest();
    }

    @AfterEach
    public void clear() {
        request = null;
    }

    @Test
    @DisplayName("生成短链接并重定向长链接")
    void getOriginUrl() {
        request.setOriginUrl("www.baidu.com");
        String shortUrl = shortUrlService.saveUrlToCache(new ExecStrategy(new HashStrategy()).getShortUrl(request.getOriginUrl()), request.getOriginUrl(), request.getOriginUrl());
        String result = shortUrlService.getOriginUrl(shortUrl);
        assertThat(result, equalTo("www.baidu.com"));
    }

    @Test
    @DisplayName("长链接未生成时404")
    void getOriginUrl1() {
        String testUrl = "unknown";
        String result = shortUrlService.getOriginUrl(testUrl);
        assertThat(result, containsString("error/404"));
    }

    @Test
    @DisplayName("生成短链接并存储")
    void saveUrlToCache() {
        request.setOriginUrl("www.google.com");
        String originUrl = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), request.getOriginUrl());
        assertThat(StringUtils.isBlank(originUrl), equalTo(true));
        //这里使用Hash策略HashStrategy
        shortUrlService.saveUrlToCache(new ExecStrategy(new HashStrategy()).getShortUrl(request.getOriginUrl()), request.getOriginUrl(), request.getOriginUrl());
        String originUrl1 = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), request.getOriginUrl());
        assertThat(StringUtils.isBlank(originUrl1), equalTo(false));
    }

    @Test
    @DisplayName("生成短链接并存储通过增长id")
    void saveUrlToCacheById() {
        request.setOriginUrl("www.alexa.com");
        String originUrl = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), request.getOriginUrl());
        assertThat(StringUtils.isBlank(originUrl), equalTo(true));
        //这里使用id grow策略IdGrowStrategy
        shortUrlService.saveUrlToCacheById(new ExecStrategy(new IdGrowStrategy()).getShortUrl(request.getOriginUrl()), request.getOriginUrl());
        String originUrl1 = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), request.getOriginUrl());
        assertThat(StringUtils.isBlank(originUrl1), equalTo(false));

    }

    @Test
    @DisplayName("生成短链接并存储通过增长id")
    void saveUrlToCacheById2() {
        request.setOriginUrl("www.alexa122.com");
        String originUrl = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), request.getOriginUrl());
        assertThat(StringUtils.isBlank(originUrl), equalTo(true));
        //这里使用id grow策略IdGrowStrategy
        shortUrlService.saveUrlToCacheById(new ExecStrategy(new IdGrowStrategy()).getShortUrl(request.getOriginUrl()), request.getOriginUrl());
        String originUrl1 = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), request.getOriginUrl());
        assertThat(StringUtils.isBlank(originUrl1), equalTo(false));

    }

    @Test
    @DisplayName("生成短链接并存储通过增长id 二次校验")
    void saveUrlToCacheById3() {
        request.setOriginUrl("www.alexa122.com");
        String originUrl = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), request.getOriginUrl());
        //这里使用id grow策略IdGrowStrategy
        shortUrlService.saveUrlToCacheById(new ExecStrategy(new IdGrowStrategy()).getShortUrl(request.getOriginUrl()), request.getOriginUrl());
        String originUrl1 = urlDataCache.getShortUrl(ShortUrlFactory.get().getLongUrlDataCache(), request.getOriginUrl());
        assertThat(StringUtils.isBlank(originUrl1), equalTo(false));

    }
}