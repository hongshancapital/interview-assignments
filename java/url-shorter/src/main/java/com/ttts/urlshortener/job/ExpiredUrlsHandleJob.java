package com.ttts.urlshortener.job;

import com.ttts.urlshortener.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 过期短链处理任务
 */
@Slf4j
@Component
public class ExpiredUrlsHandleJob {

    private ShortUrlService shortUrlService;

    @Autowired
    public ExpiredUrlsHandleJob(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    /**
     * 每10秒触发一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void handle() {
        log.debug("开始清理过期短链数据...");
        shortUrlService.handleExpired();
    }
}
