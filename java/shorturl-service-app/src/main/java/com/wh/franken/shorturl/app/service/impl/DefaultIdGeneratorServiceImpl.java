package com.wh.franken.shorturl.app.service.impl;

import com.wh.franken.shorturl.app.service.IdGeneratorService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 本机产生发号器，重启从1开始
 * @author fanliang
 */
@Service
@Primary
public class DefaultIdGeneratorServiceImpl implements IdGeneratorService {

    private AtomicLong incr = new AtomicLong(0);

    @Override
    public long incrId() {
        return incr.incrementAndGet();
    }
}
