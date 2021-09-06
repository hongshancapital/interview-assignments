package com.sequcap.shorturl.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootTest
public class ShortUrlConvertServiceThreadTests {

    Logger log = LoggerFactory.getLogger(ShortUrlConvertServiceThreadTests.class);

    private static final String LONG_URL_PREFIXES = "https://blog.csdn.net/zhanggonglalala/article/details/";

    @Autowired
    private ThreadTaskService taskService;

    @Autowired
    private ThreadPoolTaskExecutor threadTestExecutor;

    @Test
    public void testLong2ShortMultiThread() {
        // 开始时间
        long startTime = System.currentTimeMillis();
        try {
            for (int i = 0; i < 1000000; i ++) {
                if (i % 100 == 0) { // 防止过快生成子线程，导致系统压力过大
                    Thread.sleep(100);
                }
                taskService.testLong2ShortMultiThreads(genLongUrl(i));
            }

            boolean sleepFlag = true;
            while(sleepFlag) {
                Thread.sleep(1000);
                log.info("Sleep one second, wait for all thread ended.");
                // 判断子线程是否全部执行完成
                sleepFlag = threadTestExecutor.getActiveCount() > 0 ? true : false;
            }
        } catch (InterruptedException e) {
            log.error("**** testLong2ShortMultiThread failed. ", e);
        }
        // 结束时间
        long endTime = System.currentTimeMillis();
        log.info("******* Convert longUrl to shortUrl in multi threads use times = {} millis seconds", (endTime - startTime));
    }

    private String genLongUrl(int i) {
        if (i % 3 == 0) {// 模拟热点数据
            return LONG_URL_PREFIXES + "345AAAAAA";
        }
        return LONG_URL_PREFIXES + i;
    }

}
