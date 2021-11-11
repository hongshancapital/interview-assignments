package com.julyday.shorturl.service.impl;

import com.julyday.shorturl.service.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/*
 * 默认ID生成器，workid可以通过zookeeper，redis 或者中心化的服务获取。
 */
@Component
public class DefaultIdGenerator implements IdGenerator, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(DefaultIdGenerator.class);
    private static final int SIGN_BITS_SIZE = 1;
    private static final int TIME_BITS_SIZE = 29;
    private static final int VERSION_BITS_SIZE = 3;
    private static final int WORKER_BITS_SIZE = 15;
    private static final int CLOCK_CALLBACK_BITS_SIZE = 2;
    private static final int SEQ_BITS_SIZE = 14;
    private final long MAX_DELTASECONDS = 536870911L;
    private final long MAX_WORKER_ID = 32767L;
    private final long MAX_CLOCK_CALLBACK = 3L;
    private final long MAX_SEQUENCE = 16383L;
    private final int TIMESTAMP_SHIFT = 34;
    private final int VERSION_SHIFT = 31;
    private final int WORKER_ID_SHIFT = 16;
    private final int CLOCK_CALLBACK_SHIFT = 14;
    protected static final long VERSION = 1L;
    private long WORKER_ID;
    // 默认开始时间 2021-01-01 00:00:00
    private final long EPOCH_SECONDS = 1609430400L;
    private long lastSecond = -1L;
    private long clockCallback = 0L;
    private long sequence = 0L;

    public DefaultIdGenerator() {
    }

    public synchronized long nextId() {
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long deltaSeconds;
        if (currentSecond < this.lastSecond) {
            deltaSeconds = this.lastSecond - currentSecond;
            this.clockCallback = this.clockCallback + 1L & 3L;
            log.warn("id_error 时钟回拨。workerId:%d, 当前时间:%d, 回拨: %d 秒", new Object[]{this.WORKER_ID, currentSecond, deltaSeconds});
        }

        this.sequence = this.sequence + 1L & 16383L;
        this.lastSecond = currentSecond;
        deltaSeconds = currentSecond - 1609430400L;
        return deltaSeconds << 7 | 2147483648L | this.WORKER_ID << 16 | this.clockCallback << 14 | this.sequence;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Long workerId = (long) Math.random() * 64;
        this.WORKER_ID = workerId & 32767L;
    }
}
