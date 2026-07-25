package com.sequcap.shorturl.service;

import com.sequcap.shorturl.web.model.UrlModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class ThreadTaskService {

    Logger log = LoggerFactory.getLogger(ThreadTaskService.class);

    @Autowired
    private ShortUrlConvertService convertService;

    @Async(value = "threadTestExecutor")
    public void testLong2ShortMultiThreads(String longUrl) {
        // 开始时间
        long startTime = System.currentTimeMillis();
        UrlModel urlModel = convertService.long2Short(longUrl);
        // 结束时间
        long endTime = System.currentTimeMillis();
        log.info("******* Convert longUrl to shortUrl use times = {} millis seconds, longUrl = {}, shortUrl = {}", (endTime - startTime), longUrl, urlModel.getShortUrl());
    }
}
