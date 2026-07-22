package com.yuanyang.hongshan.util;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author yuanyang
 * @description
 * @since 2021/12/15 17:08
 */
public class JavaSnowflake {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSnowflake.class);

    private final LongAdder genCounter = new LongAdder();

    private final LongAdder exceptionCounter = new LongAdder();

    private final long workerIdBits = 5L;
    private final long dataCenterIdBits = 5L;
    private final long sequenceBits = 12L;

    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    private static final int R_SEED = 100;

    private static final Random R = new Random();

    @Getter
    private long sequence = 0L;

    @Getter
    private long lastTimestamp = -1L;
    private final long epoch;
    private final long workerId;
    private final long dataCenterId;

    public JavaSnowflake(long workerId, long dataCenterId) {
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.epoch = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toEpochSecond(ZoneOffset.of("+8"));
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (lastTimestamp == timestamp) {
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
        } else {
            sequence = R.nextInt(R_SEED);
        }
        lastTimestamp = timestamp;
        genCounter.increment();
        long dataCenterIdShift = sequenceBits + workerIdBits;
        return ((timestamp - epoch) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << sequenceBits) |
                sequence;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
