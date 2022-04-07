package com.sequoia.infrastructure.util;

import com.alibaba.testable.core.annotation.MockInvoke;
import com.alibaba.testable.processor.annotation.EnablePrivateAccess;
import com.sequoia.infrastructure.common.exception.TinyCodeException;
import com.sequoia.infrastructure.service.impl.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static com.alibaba.testable.core.tool.PrivateAccessor.set;

/**
 * SnowflakeIdWorker1Test
 *
 * @author KVLT
 * @date 2022-04-05
 *  */
@Slf4j
@EnablePrivateAccess(srcClass = SnowflakeIdWorker.class)
public class SnowflakeIdWorkerTest {

    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();

    public static class Mock {

        @MockInvoke(targetClass = SnowflakeIdWorker.class)
        private String getHostName() {
            throw new TinyCodeException("未知host");
        }
    }

    @Test
    public void testInitMachineId() {
        snowflakeIdWorker.initMachineId();
    }

    @Test
    public void testNextId() {
//        set(snowflakeIdWorker, "pi", 3.14);
        set(snowflakeIdWorker, "lastTimestamp", System.currentTimeMillis()/1000 + 1000);

        long SEQUENCE_BIT = 16;
        set(snowflakeIdWorker, "sequence", 1 << SEQUENCE_BIT - 1);
        snowflakeIdWorker.nextId();
    }
}
