package com.scdt.shortlink.util.sequence;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xbhong
 * @date 2022/4/17 18:03
 * @Description 基于AtomicLong方法的发号器
 */
@RequiredArgsConstructor
public class AtomicLongSequenceGenerator implements SequenceGenerator{

    private AtomicLong counter = new AtomicLong(100000);

    @Override
    public long generate() {
        return counter.getAndIncrement();
    }
}
