package com.scdt.china.shorturl.repository.id;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnowflakeIdGeneratorTest {

    private static final Logger LOG = LoggerFactory.getLogger(SnowflakeIdGeneratorTest.class);

    @Test
    void invalidInit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SnowflakeIdGenerator(-1, 1, System.currentTimeMillis()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SnowflakeIdGenerator(1, -1, System.currentTimeMillis()));
    }

    @Test
    public void nextId() {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1, System.currentTimeMillis());
        for (int i = 0; i < 1000000; i++) {
            snowflakeIdGenerator.nextId();
        }
    }


}