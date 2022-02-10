package com.ccb.domain.generate.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * @Author: nieyy
 * @Date: 2021/7/24 16:02
 * @Version 1.0
 * @Description: 使用两个缓存存储长短域名，方便统计，如果域名本身长度小于等于8，则不生成短域名。
 */
@Component
public class ShorterStorageMemory {

    private static Logger logger = LoggerFactory.getLogger(ShorterStorageMemory.class);

    //key是短域名，value是长域名
    LocalCache shortDomainCache = new LocalCache(100);

    //key是长域名，value是短域名
    LocalCache longDomainCache = new LocalCache(100);


    //根据短域名获取长域名，key不存在返回null,在业务代码中进行判断
    public String getLongDomain(String shortDomain){
        return (String) shortDomainCache.getCache(shortDomain);
    }


    //根据长域名获取短域名，key不存在返回null,在业务代码中进行判断
    public String getShortDomain(String longDomain){
        return (String) longDomainCache.getCache(longDomain);
    }

    public void save(String shortDomain, String longDomain,int expireSecond) {
        expireSecond = expireSecond * 1000;
        shortDomainCache.setCache(shortDomain, longDomain,expireSecond);
        longDomainCache.setCache(longDomain, shortDomain,expireSecond);
    }

    /**
     * 开启清理过期缓存的线程
     */
    @PostConstruct
    private void startCleanThread() {
        CleanTimeOutThread cleanTimeOutThread = new CleanTimeOutThread(shortDomainCache,longDomainCache);
        Thread thread = new Thread(cleanTimeOutThread);
        //设置为后台守护线程
        thread.setDaemon(true);
        thread.start();
    }



    /**
     * 每一分钟清理一次过期缓存
     */
    static class CleanTimeOutThread implements Runnable{
        private Logger logger = LoggerFactory.getLogger(CleanTimeOutThread.class);

        private LocalCache shortDomainCache;

        private LocalCache longDomainCache;

        private CleanTimeOutThread(LocalCache shortDomainCache, LocalCache longDomainCache ){
            this.shortDomainCache = shortDomainCache;
            this.longDomainCache = longDomainCache;

        }

        /**
         * 时间1分钟
         */
        private final int spinInterval =  60 * 1000;


        @Override
        public void run() {
            while (true) {
                logger.info("clean thread run ");
                shortDomainCache.deleteTimeOut();
                longDomainCache.deleteTimeOut();
                try {
                    Thread.sleep(spinInterval);
                } catch (InterruptedException e) {
                    logger.info("线程休眠发生异常");
                }
            }
        }

    }





}
