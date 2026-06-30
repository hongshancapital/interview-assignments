package org.example.shorturl.generator.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.generator.IdGenerator;

import java.util.Random;

/**
 * Snowflake Id生成器。该实现借鉴美团的leaf。
 *
 * @author bai
 * @date 2021/6/19 0:58
 */
@Slf4j
public class MyIdGenerator
        implements IdGenerator {
    /** twepoch */
    private static final long TWEPOCH = (DateUtil.thisYear() - 1970) * 12 * 30 * 24 * 60 * 60 * 1000L;
    /** 位序列 */
    private static final long SEQUENCE_BITS = 12L;
    /** 序列的最大 */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /** 序列 */
    private long sequence = 0L;
    /** 最后一个时间戳 */
    private long lastTimestamp = -1L;
    /** 随机 */
    private static final Random RANDOM = RandomUtil.getRandom();
    
    /**
     * 雪花id生成器
     */
    public MyIdGenerator(Integer workerId) {
    }
    
    /**
     * 获取数值型分布式Id。
     *
     * @return 生成后的Id。
     */
    @Override
    public synchronized long nextLongId() {
        long timestamp = timeGen();
        int maxGap = 10;
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // seq 为0的时候表示是下一毫秒时间开始对seq做随机
                sequence = RANDOM.nextInt(100);
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        else {
            // 如果是新的ms开始
            sequence = RANDOM.nextInt(100);
        }
        lastTimestamp = timestamp;
        return ((timestamp - TWEPOCH) << SEQUENCE_BITS) | sequence;
    }
    
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
    
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
