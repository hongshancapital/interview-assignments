package com.yuanyang.hongshan.util;

import lombok.RequiredArgsConstructor;

/**
 * @author yuanyang
 * @description 雪花算法序列生成器
 * @since 2021/12/15 17:14
 */

@RequiredArgsConstructor
public class SnowflakeSequenceGenerator implements SequenceGenerator {

    private final long workerId;
    private final long dataCenterId;

    private JavaSnowflake javaSnowflake;

    public void init() {
        this.javaSnowflake = new JavaSnowflake(workerId, dataCenterId);
    }

    @Override
    public long generate() {
        return javaSnowflake.nextId();
    }
}