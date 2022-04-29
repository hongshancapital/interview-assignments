package com.hszb.shorturl.manager.generate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/18 2:40 下午
 * @Version: 1.0
 * @Description: 自增ID分发器
 */

public class IdGenerator {

    private final static AtomicLong idNum = new AtomicLong(0);

    private IdGenerator() {
    }

    private static class IdGeneratorHolder {
        private static final IdGenerator instance = new IdGenerator();
    }

    public static IdGenerator getInstance() {
        return IdGeneratorHolder.instance;
    }

    public long getNextId() {
        return idNum.incrementAndGet();
    }

}
