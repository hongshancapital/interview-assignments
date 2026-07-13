package com.github.shwas1.shorturl.service.generator.snowflake;

/**
 * 比特分配器
 */
class BitsAllocator {

    /**
     * 总比特长度
     */
    private final int totalBits;

    /**
     * 时间戳最大值
     */
    private final long maxDeltaTimes;

    /**
     * 序列号最大值
     */
    private final long maxSequenceValue;

    /**
     * timestamp偏移量
     */
    private final int timestampShift;

    /**
     * workId偏移量
     */
    private final int workIdShift;

    /**
     * Constructor with timestampBits, randomBits, sequenceBits<br>
     */
    BitsAllocator(int totalBits, int timestampBits, int workIdBits, int sequenceBits) {
        this.totalBits = totalBits;
        this.maxDeltaTimes = ~(-1L << timestampBits);
        this.maxSequenceValue = ~(-1L << sequenceBits);

        // initialize shift
        this.timestampShift = sequenceBits + workIdBits;
        this.workIdShift = sequenceBits;
    }

    /**
     * Allocate bits for UID according to delta seconds & randomValue & sequence<br>
     * <b>Note that: </b>The highest bit will always be 0 for sign
     */
    long allocate(long currentTimeMillis, long workId, long sequence) {
        return 1L << (totalBits - 1) | (currentTimeMillis << timestampShift) | (workId << workIdShift) | sequence;
    }

    long getMaxDeltaTimes() {
        return maxDeltaTimes;
    }

    /**
     * 判断当前uuid是否溢出
     */
    boolean isSequenceValueOverflow(long uuid) {
        return (uuid & maxSequenceValue) == maxSequenceValue;
    }

}