package com.mortimer.shortenurl.component.sequence;

import com.mortimer.shortenurl.util.Base62;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleSequenceGenerator implements SequenceGenerator {
    // 初始值可以调整
    private AtomicLong counter;

    public SimpleSequenceGenerator(long initVal) {
        counter = new AtomicLong(initVal);
    }

    @Override
    public long nextId() {
        return counter.getAndIncrement();
    }
}
