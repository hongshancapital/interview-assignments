package com.hongshan.interfacejob.config;

import com.hongshan.interfacejob.service.impl.MatchStorageByLocalRamServiceImpl;
import com.hongshan.interfacejob.test.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 系统初始化
 * */
@Component
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MatchStorageByLocalRamServiceImpl matchStorageByLocalRamServiceImpl;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /**系统初始加载域名信息*/
        matchStorageByLocalRamServiceImpl.setUrlMatch(TestData.SHORT_URL_BAIDU,TestData.LONG_URL_BAIDU);
        matchStorageByLocalRamServiceImpl.setUrlMatch(TestData.SHORT_URL_SINA,TestData.LONG_URL_SINA);
    }
}