package com.scdt.sdn.util;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class SnowUtils {
    private static final int minNum = 1024;
    private static final AtomicInteger bigAtomic = new AtomicInteger(minNum);
    private static Long recordSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));

    /**
     * 获取15位长度的自增长Key
     * @return long
     */
    public Long nextId() {
        if (LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) != recordSecond) {
            recordSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            bigAtomic.set(minNum);
        }
        String differ = String.valueOf(recordSecond);
        int atomic = bigAtomic.getAndIncrement();
        if (atomic == 9999) {
            String text = "bigKey超出阈值:" + LocalDateTime.now().toString();
            throw new RuntimeException(text);
        }
        return new BigInteger(differ + atomic).longValue();
    }
}
