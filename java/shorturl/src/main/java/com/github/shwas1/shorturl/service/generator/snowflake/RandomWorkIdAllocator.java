package com.github.shwas1.shorturl.service.generator.snowflake;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机生成工作Id
 * 说明：如果用于企业级分布式环境下，需依赖外部中间件（比如Zookeeper）保证workId的唯一性。
 */
@Component
public class RandomWorkIdAllocator implements WorkIdAllocator {
    @Override
    public long getWorkId(int workIdBits) {
        return ThreadLocalRandom.current().nextLong((~(-1L << workIdBits) + 1));
    }
}
