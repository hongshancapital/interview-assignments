package com.youming.sequoia.sdn.apipublic.manager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * id生成器
 */
@Component
public class IdGeneratorManager {

    private static final Logger logger = LoggerFactory.getLogger(IdGeneratorManager.class);

    /**
     * 为了提高并发性能，使用了两个计数器，计数器的数量可以增加，但是考虑到多线程以往的并发性能，计数器的数量超过当前机器的逻辑线程的2倍意义就不大了。这里为了示范，使用了2个。
     */
    private static final AtomicLong count1 = new AtomicLong(10000);

    private static final AtomicLong count2 = new AtomicLong(10001);

    //步进数应等于计数器的数量
    private static long step = 2L;

    public long getId() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        long randNum = threadLocalRandom.nextLong(step);
        long id = 0;
        if (randNum == 0L) {
            id = count1.getAndAdd(step);
        } else if (randNum == 1L) {
            id = count2.getAndAdd(step);
        }
        id = count1.getAndAdd(step);
        return id;
    }


}
