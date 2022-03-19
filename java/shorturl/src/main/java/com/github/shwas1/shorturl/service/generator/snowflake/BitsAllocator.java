package com.github.shwas1.shorturl.service.generator.snowflake;

/**
 * 比特分配器
 */
class BitsAllocator {
    private final int totalBits;

    private final long maxDeltaTimes;

    private final long maxSequenceValue;

    /**
     * Shift for timestamp & randomValue
     */
    private final int timestampShift;

    /**
     * Constructor with timestampBits, randomBits, sequenceBits<br>
     */
    BitsAllocator(int totalBits, int timestampBits, int sequenceBits) {
        this.totalBits = totalBits;
        this.maxDeltaTimes = ~(-1L << timestampBits);
        this.maxSequenceValue = ~(-1L << sequenceBits);

        // initialize shift
        this.timestampShift = sequenceBits;
    }

    /**
     * Allocate bits for UID according to delta seconds & randomValue & sequence<br>
     * <b>Note that: </b>The highest bit will always be 0 for sign
     */
    long allocate(long deltaSeconds, long sequence) {
        return 1L << (totalBits - 1) | (deltaSeconds << timestampShift) | sequence;
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