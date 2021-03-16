package com.sequoiacap.domain.common;

import lombok.RequiredArgsConstructor;

/**
 * 雪花算法序列生成器
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
