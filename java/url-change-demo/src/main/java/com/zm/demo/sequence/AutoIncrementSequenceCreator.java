package com.zm.demo.sequence;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

/**
 * @ClassName IncrSequenceCreator
 * @Description 以自增方式创建序号的发号器
 * @Author zhaomin
 * @Date 2021/10/29 18:05
 **/
@Component
public class AutoIncrementSequenceCreator implements SequenceCreator{

    public static final long DEFAULT_BEGIN_VALUE = 0L;

    private AtomicLong sequenceID = new AtomicLong(DEFAULT_BEGIN_VALUE);

    public void setSequenceID(AtomicLong sequenceID) {
        this.sequenceID = sequenceID;
    }

    /**
     * 以自增方式创建序号
     * @Param: []
     * @return: java.lang.Long
     * @Author: zhaomin
     * @Date: 2021/10/15 10:24
     */
    @Override
    public Long createSeq(long maxThreshold) {
        long seq = sequenceID.incrementAndGet();
        if (seq >= maxThreshold) {
            sequenceID.compareAndSet(seq, DEFAULT_BEGIN_VALUE);
            seq = sequenceID.incrementAndGet();
        }

        return seq;
    }
}
