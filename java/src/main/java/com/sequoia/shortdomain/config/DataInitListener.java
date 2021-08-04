package com.sequoia.shortdomain.config;

import com.sequoia.shortdomain.common.ShortDomainCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataInitListener implements ApplicationListener<ContextRefreshedEvent> {//ContextRefreshedEvent为启动事件

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitListener.class);
    @Autowired
    private CommonConfig config;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ShortDomainCache.init(config.getShortDomainCacheMax(),config.getShortDomainCacheMin(),config.getShortDomainCacheClearNum());
    }
}
