package com.ascmix.surl.utils;

// snowflake from Facebook
public class IdGenerator {
    private static final long START_AT = 1610462842000L;

    private static class Holder {
        private static final IdGenerator INSTANCE = new IdGenerator();
    }

    public static IdGenerator getInstance() {
        return Holder.INSTANCE;
    }

    private static final int TIMESTAMP_LEN = 40;
    private static final int DATACENTER_ID_LEN = 4;
    private static final int WORKER_ID_LEN = 6;
    private static final int SEQ_LEN = 12;
    private static final int BLANK_LEN = 64 - TIMESTAMP_LEN - DATACENTER_ID_LEN - WORKER_ID_LEN - SEQ_LEN;

    private static final int MAX_SEQ = 1 << SEQ_LEN;

    private static final int DATACENTER_ID_SHIFT = SEQ_LEN + WORKER_ID_LEN;
    private static final int WORKER_ID_SHIFT = SEQ_LEN;
    private static final int TIMESTAMP_SHIFT = SEQ_LEN + WORKER_ID_LEN + DATACENTER_ID_LEN;

    private final int workerId;
    private final int datacenterId;
    private int seq = 0;

    private long lastTimestamp = -1;

    private IdGenerator() {
        // PROD ENV: read from environment
        workerId = 1;
        datacenterId = 1;
    }

    private long nextMs(long lastTimestamp) {
        long currentMs = System.currentTimeMillis();
        while (currentMs == lastTimestamp) {
            currentMs = System.currentTimeMillis();
        }
        return currentMs;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("unexpect! timestamp: [%d] lastTimestamp: [%d]", timestamp, lastTimestamp));
        }

        if (timestamp == lastTimestamp) {
            seq++;
            if (seq == MAX_SEQ) {
                timestamp = nextMs(lastTimestamp);
                seq = 0;
            }
        } else {
            seq = 0;
        }

        lastTimestamp = timestamp;

        long id = ((timestamp - START_AT) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | seq;

        id <<= BLANK_LEN;
        id >>= BLANK_LEN;
        return id;
    }

}
