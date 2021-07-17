package com.xing.shortlink;

import com.xing.shortlink.domain.http.request.CreateShortUrlRequest;
import com.xing.shortlink.domain.http.response.CreateShortUrlResponse;
import com.xing.shortlink.domain.http.request.QueryOriginalUrlRequest;
import com.xing.shortlink.domain.http.response.QueryOriginalUrlResponse;
import com.xing.shortlink.domain.http.Result;
import com.xing.shortlink.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ShortLinkApplicationTests {

    @Autowired
    UrlService urlService;

    @Test
    void createShortUrl() {
        CreateShortUrlRequest request = new CreateShortUrlRequest();
        request.setOriginalUrl("http://www.baidu.com");
        Result<CreateShortUrlResponse> response = urlService.createShortUrl(request);
        log.info("response:{},短链接地址:{}", response.toString(), response.getData().getShortUrl());

        Result<CreateShortUrlResponse> response2 = urlService.createShortUrl(request);
        log.info("response:{},短链接地址:{}", response2.toString(), response2.getData().getShortUrl());
    }

    @Test
    void queryOriginalUrl() {
        QueryOriginalUrlRequest queryRequest = new QueryOriginalUrlRequest();
        queryRequest.setShortUrl("http://c.cn/adsAd");
        Result<QueryOriginalUrlResponse> queryResponse = urlService.queryOriginalUrl(queryRequest);
        log.info("返回数据:{}", queryResponse.toString());

        CreateShortUrlRequest createRequest = new CreateShortUrlRequest();
        createRequest.setOriginalUrl("http://www.baidu.com");
        Result<CreateShortUrlResponse> createResponse = urlService.createShortUrl(createRequest);
        log.info("response:{},短链接地址:{}", createResponse.toString(), createResponse.getData().getShortUrl());

        queryRequest.setShortUrl(createResponse.getData().getShortUrl());
        queryResponse = urlService.queryOriginalUrl(queryRequest);
        log.info("response:{},原始地址:{}", queryResponse.toString(), queryResponse.getData().getOriginalUrl());
    }


}
