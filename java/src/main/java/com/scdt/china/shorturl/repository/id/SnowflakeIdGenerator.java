package com.scdt.china.shorturl.repository.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 雪花算法ID生成器
 *
 * @author ng-life
 * @see <a href="https://github.com/twitter-archive/snowflake/blob/snowflake-2010/src/main/scala/com/twitter/service/snowflake/IdWorker.scala">Twitter Snowflake IdWorker</a>
 */
public class SnowflakeIdGenerator implements IdGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(SnowflakeIdGenerator.class);

    private final int workerIdBits = 5;
    private final int datacenterIdBits = 5;
    private final int sequenceBits = 12;
    private final int workerIdShift = sequenceBits;
    private final int datacenterIdShift = sequenceBits + workerIdBits;
    private final int timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final long maxWorkerId = ~(-1L << workerIdBits);
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);
    private final long sequenceMask = ~(-1L << sequenceBits);

    private final long twepoch;
    private final long workerId;
    private final long datacenterId;
    private long sequence = 0;
    private long lastTimestamp = -1;


    public SnowflakeIdGenerator(int datacenterId, int workerId, long twepoch) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
        this.twepoch = twepoch;
        LOG.info("worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid {}",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);
    }

    @Override
    public synchronized Long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new InvalidSystemClockException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }


    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}


