package com.scdt.cache.lru;

import com.scdt.cache.utils.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class LRUCachePerfTest {


    /********************* configurable parameters *********************/
    private static final int ITEM_COUNT = 1000000;
    private static final int LOOP = 5;
    private static final int PRODUCER_COUNT = 4;
    private static final int CONSUMER_COUNT = 4;
    private static final int STRING_LEN = 16;
    /******************************************************************/

    private final AtomicInteger producingItemCount = new AtomicInteger(0);
    private final AtomicInteger consumingItemCount = new AtomicInteger(0);
    private final BlockingQueue<String> keysInMemoryQueue = new LinkedBlockingQueue<String>();

    private static LRUCache<String, String> cache;

    @Test
    public void testProduceThenConsume() throws InterruptedException {
        System.out.println("LruCache performance test begin ...");
        for (int i = 0; i < LOOP; i++) {
            cache = new LRUCache<>();
            System.out.println("[doRunProduceThenConsume] round " + (i + 1) + " of " + LOOP);
            this.doRunProduceThenConsume();
            producingItemCount.set(0);
            consumingItemCount.set(0);
        }
        System.out.println("[doRunProduceThenConsume] test ends");
    }

//    @Test
    public void testProduceMixedConsume() throws InterruptedException {
        for (int i = 0; i < LOOP; i++) {
            cache = new LRUCache<>();
            System.out.println("[testProduceMixedConsume] round " + (i + 1) + " of " + LOOP);
            this.doRunProduceMixedConsume();
            producingItemCount.set(0);
            consumingItemCount.set(0);
        }
        System.out.println("[testProduceMixedConsume] test ends");
    }

    private void doRunProduceThenConsume() throws InterruptedException {
        CountDownLatch producerLatch = new CountDownLatch(PRODUCER_COUNT);
        CountDownLatch consumerLatch = new CountDownLatch(CONSUMER_COUNT);
        BlockingQueue<Result> producerResults = new LinkedBlockingQueue<Result>();
        BlockingQueue<Result> consumerResults = new LinkedBlockingQueue<Result>();
        long start = System.currentTimeMillis();
        long totalProducingTime = 0;
        long totalConsumingTime = 0;

        for (int i = 0; i < PRODUCER_COUNT; i++) {
            Producer p = new Producer(producerLatch, producerResults);
            p.start();
        }

        for (int i = 0; i < PRODUCER_COUNT; i++) {
            Result result = producerResults.take();
            assertEquals(result.status, Status.SUCCESS);
            totalProducingTime += result.duration;
        }
        long duration = System.currentTimeMillis() - start;

        System.out.println("-----------------------------------------------");
        System.out.println("Producing test result:");
        System.out.printf("Total test time = %d ms.\n", duration);
        System.out.printf("Total item count = %d\n", ITEM_COUNT);
        System.out.printf("Producer thread number = %d\n", PRODUCER_COUNT);
        System.out.printf("Item message length = %d bytes\n", STRING_LEN);
        System.out.printf("Total producing time =  %d ms.\n", totalProducingTime);
        System.out.printf("Average producint time = %d ms.\n", totalProducingTime / PRODUCER_COUNT);
        System.out.println("-----------------------------------------------");

        // the consumer start
        start = System.nanoTime();
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            Consumer c = new Consumer(consumerLatch, consumerResults);
            c.start();
        }

        for (int i = 0; i < CONSUMER_COUNT; i++) {
            Result result = consumerResults.take();
            assertEquals(result.status, Status.SUCCESS);
            totalConsumingTime += result.duration;
        }
        duration = System.nanoTime() - start;
        assertEquals(producingItemCount.get(), consumingItemCount.get());
        assertTrue(keysInMemoryQueue.isEmpty());

        System.out.println("Consuming test result:");
        System.out.printf("Total test time = %d ms.\n", duration);
        System.out.printf("Total item count = %d\n", ITEM_COUNT);
        System.out.printf("Consumer thread number = %d\n", CONSUMER_COUNT);
        System.out.printf("Item message length = %d bytes\n", STRING_LEN);
        System.out.printf("Total consuming time =  %d ms.\n", totalConsumingTime);
        System.out.printf("Average consuming time = %d ms.\n", totalConsumingTime / CONSUMER_COUNT);
        System.out.println("-----------------------------------------------");
    }

    private void doRunProduceMixedConsume() throws InterruptedException {
        CountDownLatch allLatch = new CountDownLatch(PRODUCER_COUNT + CONSUMER_COUNT);
        BlockingQueue<Result> producerResults = new LinkedBlockingQueue<Result>();
        BlockingQueue<Result> consumerResults = new LinkedBlockingQueue<Result>();

        long totalProducingTime = 0;
        long totalConsumingTime = 0;

        long start = System.currentTimeMillis();
        //run testing
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            Producer p = new Producer(allLatch, producerResults);
            p.start();
        }

        for (int i = 0; i < CONSUMER_COUNT; i++) {
            Consumer c = new Consumer(allLatch, consumerResults);
            c.start();
        }

        //verify and report
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            Result result = producerResults.take();
            assertEquals(result.status, Status.SUCCESS);
            totalProducingTime += result.duration;
        }

        for (int i = 0; i < CONSUMER_COUNT; i++) {
            Result result = consumerResults.take();
            assertEquals(result.status, Status.SUCCESS);
            totalConsumingTime += result.duration;
        }

        long duration = System.currentTimeMillis() - start;

        assertEquals(producingItemCount.get(), consumingItemCount.get());

        System.out.println("-----------------------------------------------");
        System.out.printf("Total test time = %d ms.\n", duration);
        System.out.printf("Total item count = %d\n", ITEM_COUNT);
        System.out.printf("Producer thread number = %d\n", PRODUCER_COUNT);
        System.out.printf("Consumer thread number = %d\n", CONSUMER_COUNT);
        System.out.printf("Item message length = %d bytes\n", STRING_LEN);
        System.out.printf("Total consuming time =  %d ms.\n", totalConsumingTime);
        System.out.printf("Average consuming time = %d ms.\n", totalConsumingTime / CONSUMER_COUNT);
        System.out.printf("Total producing time =  %d ms.\n", totalProducingTime);
        System.out.printf("Average producing time = %d ms.\n", totalProducingTime / PRODUCER_COUNT);
        System.out.println("-----------------------------------------------");
    }

    private class Producer extends Thread {
        private final CountDownLatch latch;
        private final Queue<Result> resultQueue;

        public Producer(CountDownLatch latch, Queue<Result> resultQueue) {
            this.latch = latch;
            this.resultQueue = resultQueue;
        }

        public void run() {
            Result result = new Result();
            try {
                latch.countDown();
                latch.await();

                long start = System.currentTimeMillis();
                while (true) {
                    int count = producingItemCount.incrementAndGet();
                    if (count > ITEM_COUNT)
                        break;

                    String string = TestUtil.randomString(STRING_LEN);

                    keysInMemoryQueue.offer(string);
                    cache.put(string, string);
                }
                result.status = Status.SUCCESS;
                result.duration = System.currentTimeMillis() - start;
            } catch (Exception e) {
                e.printStackTrace();
                result.status = Status.ERROR;
            }
            resultQueue.offer(result);
        }
    }

    private class Consumer extends Thread {
        private final CountDownLatch latch;
        private final Queue<Result> resultQueue;

        public Consumer(CountDownLatch latch, Queue<Result> resultQueue) {
            this.latch = latch;
            this.resultQueue = resultQueue;
        }

        public void run() {
            Result result = new Result();
            result.status = Status.SUCCESS;
            try {
                latch.countDown();
                latch.await();

                long start = System.currentTimeMillis();
                while (true) {
                    int count = consumingItemCount.getAndIncrement();
                    if (count >= ITEM_COUNT)
                        break;

                    String key = keysInMemoryQueue.take();
                    if (key != null && !key.isEmpty()) {
                        String value = cache.get(key);
                        // wait a moment for k/v to be put in the Cache
                        // may cause dead lock
                        while (value == null) {
                            value = cache.get(key);
                        }
                        if (!key.equals(value)) {
                            result.status = Status.ERROR;
                        }
                    }
                }
                result.duration = System.currentTimeMillis() - start;
            } catch (Exception e) {
                e.printStackTrace();
                result.status = Status.ERROR;
            }
            resultQueue.offer(result);
        }
    }

    private static enum Status {
        ERROR,
        SUCCESS
    }

    private static class Result {
        Status status;
        long duration;
    }
}