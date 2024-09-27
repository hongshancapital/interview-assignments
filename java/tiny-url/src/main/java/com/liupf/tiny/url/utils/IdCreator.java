package com.liupf.tiny.url.utils;

/**
 * ID生成器，改造Snowflake算法，确保生成的ID不超过：Math.pow(64, 8) = 218340105584896 <br/>
 * 测试：有效ID可用40年，单机理论QPS：64000 <br/>
 * 生成的ID规则如下：<br/>
 * 2022年后毫秒     2业务属性     2机器码       64自增码 <br/>
 * 64时间戳        8业务码       7机器码       6序列号
 */
public class IdCreator {
    private final static long BEGIN_TIMESTAMP = 1640970060000L;
    private static long LAST_TIMESTAMP = -1L;
    private final int workerId;
    private final int dataCenterId;
    private long sequence = 0L;

    public IdCreator(int workerId) {
        if (workerId <= 1 && workerId >= 0) {
            this.workerId = workerId;
            this.dataCenterId = 0;
        } else {
            throw new IllegalArgumentException("worker Id can't be greater than 1 or less than 0");
        }
    }

    public long nextId() {
        return this.nextId(false, 0);
    }

    public long nextId(int busId) {
        if (busId <= 1 && busId >= 0) {
            return this.nextId(true, busId);
        } else {
            throw new IllegalArgumentException("data center Id can't be greater than 1 or less than 0");
        }
    }

    private synchronized long nextId(boolean isPadding, int busId) {
        long timestamp = this.timeGen();
        long paddingNum = this.dataCenterId;
        if (isPadding) {
            paddingNum = busId;
        }

        if (timestamp < LAST_TIMESTAMP) {
            String errorMsg = String.format("Clock moved backwards. Refusing to generate id for %s milliseconds",
                    (LAST_TIMESTAMP - timestamp));
            throw new RuntimeException(errorMsg);
        } else {
            this.sequence = this.sequence + 1L & 255L;
            if (LAST_TIMESTAMP == timestamp && this.sequence == 0L) {
                timestamp = this.tilNextMillis(LAST_TIMESTAMP);
            }
            LAST_TIMESTAMP = timestamp;
            return timestamp - BEGIN_TIMESTAMP << 8 | paddingNum << 7 | this.workerId << 6 | this.sequence;
        }
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
        // 2052-01-01 01:01:00年时间戳
        // return 2587654860000L;
    }
}
