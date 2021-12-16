package com.scdt.china.shorturl.repository.id;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 自增长ID生成器
 *
 * @author ng-life
 */
public class IncrementIdGenerator implements IdGenerator {

    private final AtomicLong sequence;

    private final long maxValue;

    public IncrementIdGenerator(long initialValue, long maxValue) {
        this.sequence = new AtomicLong(initialValue);
        this.maxValue = maxValue;
    }

    @Override
    public Long nextId() {
        long result = sequence.incrementAndGet();
        if (result > maxValue) {
            throw new SequenceOverflowException("自增长ID序列溢出");
        }
        return result;
    }

}
