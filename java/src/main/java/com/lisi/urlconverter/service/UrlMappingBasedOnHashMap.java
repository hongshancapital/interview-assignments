package com.lisi.urlconverter.service;

import com.lisi.urlconverter.model.UrlData;
import com.lisi.urlconverter.util.JVMUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @description: 基于ConcurrentHashMap实现的存储
 * @author: li si
 */

@Service
public class UrlMappingBasedOnHashMap implements UrlMappingService, InitializingBean, Runnable {
    /**
     * 存储长域名至短域名的映射关系 key:短域名 value:长域名
     **/
    private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    /**
     * 存储短域名过期信息，头节点->尾节点，到期时间从前向后
     **/
    private static final ConcurrentLinkedQueue<UrlData> urlQueue = new ConcurrentLinkedQueue<>();

    /**
     * 短域名过期时间
     **/
    @Value("${urlconverter.key.expire}")
    private long expireTime;

    /**
     * 定期清理单批次最多清理数据
     **/
    @Value("${urlconverter.key.clean.batch}")
    private long cleanBatch;

    /**
     * 定期清理执行周期
     **/
    @Value("${urlconverter.key.clean.interval}")
    private long cleanInterval;

    /**
     * 数据存储上限
     **/
    @Value("${urlconverter.data.threshold}")
    private long dataThreshold;

    /**
     * JVM剩余空间下限，当小于该值时，停止写入服务
     **/
    @Value("${urlconverter.jvm.threshold}")
    private long freeMemoryThreshold;

    private volatile boolean isRunning;

    @Override
    public void saveMapping(String oriUrl, String convertedUrl) {
        map.put(convertedUrl, oriUrl);
        urlQueue.offer(new UrlData(convertedUrl, expireTime));
    }

    @Override
    public String getMapping(String convertedUrl) {
        return map.get(convertedUrl);
    }

    @Override
    public boolean isEnough() {
        return map.size() < dataThreshold && JVMUtil.getFreeMemory() > freeMemoryThreshold;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.isRunning = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (this.isRunning) {
            int count = 0;
            long currentTime = System.currentTimeMillis();
            while (!urlQueue.isEmpty() && count < cleanBatch) {
                // 从队列头节点向后遍历，如节点已过期，则将其从队列和map中移除
                if (urlQueue.peek().getTtl() < currentTime) {
                    UrlData urlData = urlQueue.poll();
                    map.remove(urlData.getVal());
                    count++;
                } else {
                    break;
                }
            }
            try {
                Thread.sleep(cleanInterval);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    @PreDestroy
    public void onDestroy() {
        this.isRunning = false;
    }
}
