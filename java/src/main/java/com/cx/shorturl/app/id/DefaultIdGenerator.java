package com.cx.shorturl.app.id;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 本机产生发号器，重启从1开始，实际生产需用发号器
 */
public class DefaultIdGenerator implements IdGenerator {

    private AtomicLong incr = new AtomicLong(0);

    @Override
    public long incrId() {
        return incr.incrementAndGet();
    }
}
