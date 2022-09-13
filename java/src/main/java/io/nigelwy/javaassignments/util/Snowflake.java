package io.nigelwy.javaassignments.util;

/**
 * SnowFlake id generator, inspired by Twitter.
 *
 * @link https://developer.twitter.com/en/docs/basics/twitter-ids
 * This class should be plain java class, and not depends on any framework or other package.
 * <p>
 * 64 bits long type in java
 * |-- 1 bit not use --|-- 59 bits timestamp in milliseconds --|-- 3 bits worker id --|-- 1 bits sequence --|
 * <p>
 * all timestamp values in this class are millisecond.
 */
public class Snowflake {

    /**
     * timestamp start from Sat Apr 23 2022 12:24:02 GMT+0800 (China Standard Time)
     * DO NOT change this value !
     */
    private static final long EPOCH = 1650687842890L;

    /**
     * worker-id take 3 bits
     */
    private static final long WORKER_ID_BITS = 3L;

    /**
     * sequence take 1 bits
     */
    private static final long SEQUENCE_BITS = 1L;

    /**
     * mask for some bit operation on sequence
     */
    private static final long SEQUENCE_MASK = (1L << SEQUENCE_BITS) - 1;

    /**
     * worker-id left-shift length
     */
    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    /**
     * timestamp left-shift length
     */
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;
    public static final Long MIN_VALUE = (1L << TIMESTAMP_LEFT_SHIFT_BITS)
            | (1L << WORKER_ID_LEFT_SHIFT_BITS);
    /**
     * worker-id should not greater than this value
     */
    private static final long WORKER_ID_MAX_VALUE = (1L << WORKER_ID_BITS) - 1;
    /**
     * machine time may go backwards, if under 10 milliseconds we wait
     */
    private static final int MAX_TIMESTAMP_BACKWARDS_TO_WAIT = 10;
    private final long workerId;

    private long sequence = 0;

    private long lastTimestamp = -1L;

    /**
     * @param workerId 12-bits number
     */
    public Snowflake(long workerId) {
        if (workerId > WORKER_ID_MAX_VALUE || workerId < 1) {
            throw new IllegalArgumentException(String.format("worker id can't be greater than %d or less than 1", WORKER_ID_MAX_VALUE));
        }
        this.workerId = workerId;
    }

    public synchronized long getId() {
        long currentTimestamp = getCurrentTimestamp();
        // if clock moved back we may wait
        if (tolerateTimestampBackwardsIfNeed(currentTimestamp)) {
            currentTimestamp = getCurrentTimestamp();
        }
        // if current timestamp equals to previous one, we try to increase sequence
        if (lastTimestamp == currentTimestamp) {
            if (sequenceIncreaseIfReachLimitReset()) {
                currentTimestamp = waitUntilNextTime(currentTimestamp);
            }
        }
        // we go into to new timestamp, reset sequence
        else {
            sequence = 0L;
        }
        lastTimestamp = currentTimestamp;
        return ((currentTimestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS)
                | (workerId << WORKER_ID_LEFT_SHIFT_BITS)
                | sequence;
    }

    private boolean tolerateTimestampBackwardsIfNeed(long curTimestamp) {
        if (lastTimestamp <= curTimestamp) {
            return false;
        }
        long timeDifference = lastTimestamp - curTimestamp;
        if (timeDifference < MAX_TIMESTAMP_BACKWARDS_TO_WAIT) {
            waitUntilNextTime(lastTimestamp);
        } else {
            throw new IllegalStateException("Machine time goes backwards more than 10 millis");
        }
        return true;
    }

    private boolean sequenceIncreaseIfReachLimitReset() {
        return 0L == (sequence = (sequence + 1) & SEQUENCE_MASK);
    }

    private long waitUntilNextTime(long timestampToContinue) {
        long timestamp;
        do {
            timestamp = getCurrentTimestamp();
        } while (timestamp <= timestampToContinue);
        return timestamp;
    }

    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}
