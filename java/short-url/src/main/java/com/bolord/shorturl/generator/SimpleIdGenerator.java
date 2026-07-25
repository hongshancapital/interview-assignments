package com.bolord.shorturl.generator;

import java.util.concurrent.atomic.LongAdder;

/**
 * 简单自增 ID 生成器
 */
public class SimpleIdGenerator implements IdGenerator {

    private final LongAdder adder = new LongAdder();

    @Override
    public long generateId() {
        adder.increment();
        return adder.longValue();
    }

}
