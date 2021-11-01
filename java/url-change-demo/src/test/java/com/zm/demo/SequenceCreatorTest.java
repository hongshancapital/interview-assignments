package com.zm.demo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zm.demo.sequence.AutoIncrementSequenceCreator;

/**
 * @ClassName LRUCacheTest
 * @Author zhaomin
 * @Date 2021/11/01 11:23
 **/
@SpringBootTest
public class SequenceCreatorTest {

    @Test
    void shortUrlOutOfBounds() throws InterruptedException {
        long maxThreshold = (long) Math.pow(62, 8);
        AutoIncrementSequenceCreator sequenceCreator = new AutoIncrementSequenceCreator();
        sequenceCreator.setSequenceID(new AtomicLong(maxThreshold-1));

        CountDownLatch cdl = new CountDownLatch(1);
        Set<Long> seqSet = new HashSet<>();

        int num = 20;
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long seq = sequenceCreator.createSeq(maxThreshold);
                //System.out.println("seq=" + seq);
                seqSet.add(seq);
            }).start();
        }

        Thread.currentThread().sleep(500);
        cdl.countDown();
        Thread.currentThread().sleep(2000);

        Assertions.assertEquals(seqSet.size(), num);
    }


}
