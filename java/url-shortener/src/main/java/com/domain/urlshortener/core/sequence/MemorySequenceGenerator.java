package com.domain.urlshortener.core.sequence;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: 内存式发号器
 * @author: rocky.hu
 * @date: 2022/4/5 1:45
 */
public class MemorySequenceGenerator implements SequenceGenerator {

    private AtomicLong counter = new AtomicLong(1000);

    @Override
    public Long generateSequenceNo() {
        return counter.getAndIncrement();
    }

}
