package com.lisi.urlconverter.service;

import com.lisi.urlconverter.model.Sequence;
import com.lisi.urlconverter.util.Long62Util;
import com.lisi.urlconverter.util.RandomUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 时间戳+序列号+随机字符串 生成id
 * 注意：该方法仅适用与单机部署环境，集群部署请选择其它方法
 * 由于短域名长度限制，取时间戳long数值的36-64位,转换成长度为5的62进制,
 * 取序列号的后11位，映射长度为长度为2的62进制，
 * 中间插入随机字符
 * @author: li si
 */

@Service
public class GenerateUrlBasedOnTime implements GenerateUrlService, InitializingBean, Runnable {

    /**
     * 当前序列号
     */
    private static final Sequence<AtomicLong> number = new Sequence<>(new AtomicLong());

    /**
     * 随机字符串，插入至短域名中
     */
    private static final Sequence<Character> randomCharacter = new Sequence<>('A');

    private static final int TIME_MASK = (1 << 29) - 1;
    private static final int NUMBER_MASK = (1 << 11) - 1;
    private static final int STR_CHANGE_INTERVAL = 1000;
    private volatile boolean isRunning;

    @Override
    public String getUrl() {
        return Long62Util.to62StringRevert(processTime(System.currentTimeMillis())) +
                randomCharacter.getValue() +
                Long62Util.to62StringRevert(processNumber(number.getValue().incrementAndGet()));
    }

    /**
     * 取时间戳的26-54位
     * @param timestamp
     * @return
     */
    private long processTime(long timestamp){
        return timestamp & TIME_MASK;
    }

    private long processNumber(long number){
        return number & NUMBER_MASK;
    }

    @Override
    public void run() {
        while (isRunning) {
            randomCharacter.setValue(RandomUtil.getRandomCharacter());
            try {
                Thread.sleep(STR_CHANGE_INTERVAL);
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        isRunning = true;
        new Thread(this).start();
    }

    @PreDestroy
    public void onDestroy() {
        isRunning = false;
    }
}
