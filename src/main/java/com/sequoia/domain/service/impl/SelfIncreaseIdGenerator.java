package com.sequoia.domain.service.impl;

import com.sequoia.domain.service.IdGenerator;
import com.sequoia.domain.util.NumericUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 自增ID分配算法
 */
@Service
@ConditionalOnProperty(prefix = "app.id.generator", name = "algorithm", havingValue = "self_increase")
public class SelfIncreaseIdGenerator implements IdGenerator {
    private static final long MAX_VALUE = NumericUtils.toDecimal("FFFFFFFF");

    private final AtomicLong sequence;

    public SelfIncreaseIdGenerator(@Value("${app.id.generator.init_value:0}") long initValue) {
        this.sequence = new AtomicLong(initValue);
    }

    @Override
    public long nextId() {
        long result = sequence.incrementAndGet();
        if (result > MAX_VALUE) {
            throw new IndexOutOfBoundsException("There are no more URLs to assign");
        }
        return result;
    }
}
