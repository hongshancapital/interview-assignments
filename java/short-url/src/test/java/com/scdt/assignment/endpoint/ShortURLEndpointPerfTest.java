package com.scdt.assignment.endpoint;

import com.scdt.assignment.application.ShortURLApplicationService;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortURLEndpointPerfTest {


    /********************* configurable parameters *********************/
    private static final int ITEM_COUNT = 100_0000;
    private static final int LOOP = 5;
    private static final int PRODUCER_COUNT = 4;
    private static final int CONSUMER_COUNT = 4;
    /******************************************************************/

    private final AtomicInteger producingItemCount = new AtomicInteger(0);
    private final AtomicInteger consumingItemCount = new AtomicInteger(0);
    private final BlockingQueue<String> keysInMemoryQueue = new LinkedBlockingQueue<String>();

    private static ShortURLApplicationService applicationService;

    @Test
    public void testProduceMixedConsume() throws InterruptedException {
        for (int i = 0; i < LOOP; i++) {
            applicationService = new ShortURLApplicationService();
            System.out.println("[testProduceMixedConsume] round " + (i + 1) + " of " + LOOP);
            this.doRunProduceMixedConsume();
            producingItemCount.set(0);
            consumingItemCount.set(0);
        }
        System.out.println("[testProduceMixedConsume] test ends");
    }

    private void doRunProduceMixedConsume() throws InterruptedException {
        CountDownLatch allLatch = new CountDownLatch(PRODUCER_COUNT + CONSUMER_COUNT);
        BlockingQueue<Result> producerResults = new LinkedBlockingQueue<Result>();
        BlockingQueue<Result> consumerResults = new LinkedBlockingQueue<Result>();

        long totalProducingTime = 0;
        long totalConsumingTime = 0;

        long avgProducingTime = 0;
        long minProducingTime = Long.MAX_VALUE;
        long maxProducingTime = Long.MIN_VALUE;

        long avgConsumingTime = 0;
        long minConsumingTime = Long.MAX_VALUE;
        long maxConsumingTime = Long.MIN_VALUE;

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
//            assertEquals(result.status, Status.SUCCESS);
            totalProducingTime += result.totalDuration;
            avgProducingTime += result.avgDuration;
            minProducingTime = Math.min(minProducingTime, result.minDuration);
            maxProducingTime = Math.max(maxProducingTime, result.maxDuration);
        }


        avgProducingTime /= PRODUCER_COUNT;

        for (int i = 0; i < CONSUMER_COUNT; i++) {
            Result result = consumerResults.take();
//            assertEquals(result.status, Status.SUCCESS);
            totalConsumingTime += result.totalDuration;
            avgConsumingTime += result.avgDuration;
            minConsumingTime = Math.min(minConsumingTime, result.minDuration);
            maxConsumingTime = Math.max(maxConsumingTime, result.maxDuration);
        }
        avgConsumingTime /= CONSUMER_COUNT;

        long duration = System.currentTimeMillis() - start;

        assertEquals(producingItemCount.get(), consumingItemCount.get());

        System.out.println("-----------------------------------------------");
        System.out.printf("Total test time = %d ms.\n", duration);
        System.out.printf("Total item count = %d\n", ITEM_COUNT);
        System.out.printf("Producer thread number = %d\n", PRODUCER_COUNT);
        System.out.printf("Consumer thread number = %d\n", CONSUMER_COUNT);
        System.out.printf("Total consuming time =  %d ms.\n", totalConsumingTime);
        System.out.printf("Average consuming time = %d ms.\n", totalConsumingTime / CONSUMER_COUNT);
        System.out.printf("Average consuming time per item = %d ms.\n", avgConsumingTime);
        System.out.printf("Min consuming time per item  = %d ms.\n", minConsumingTime);
        System.out.printf("Max consuming time per item = %d ms.\n", maxConsumingTime);

        System.out.printf("Total producing time =  %d ms.\n", totalProducingTime);
        System.out.printf("Average producing time = %d ms.\n", totalProducingTime / PRODUCER_COUNT);
        System.out.printf("Average producing time per item = %d ms.\n", avgProducingTime);
        System.out.printf("Min producing time per item = %d ms.\n", minProducingTime);
        System.out.printf("Max producing time per item = %d ms.\n", maxProducingTime);

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
                    long s = System.currentTimeMillis();
                    int count = producingItemCount.incrementAndGet();
                    if (count > ITEM_COUNT)
                        break;

                    String string = UUID.randomUUID().toString();

                    keysInMemoryQueue.offer(string);
                    applicationService.getShortUrl(string);
                    result.maxDuration = Math.max(result.maxDuration, System.currentTimeMillis() - s);
                    result.minDuration = Math.min(result.minDuration, System.currentTimeMillis() - s);
                }
                result.status = Status.SUCCESS;
                result.totalDuration = System.currentTimeMillis() - start;
                result.avgDuration = (result.totalDuration * 1.0) / consumingItemCount.get();
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
            try {
                latch.countDown();
                latch.await();

                long start = System.currentTimeMillis();
                while (true) {
                    long s = System.currentTimeMillis();
                    int count = consumingItemCount.getAndIncrement();
                    if (count >= ITEM_COUNT)
                        break;

                    String key = keysInMemoryQueue.take();
                    if (key != null && !key.isEmpty()) {
                        String value = applicationService.getOriginUrl(key);
                        if (value == null || !key.equals(value)) {
                            result.status = Status.ERROR;
                            result.error ++;
                        } else {
                            result.success ++;
                        }
                    }
                    result.maxDuration = Math.max(result.maxDuration, System.currentTimeMillis() - s);
                    result.minDuration = Math.min(result.minDuration, System.currentTimeMillis() - s);
                }
                result.totalDuration = System.currentTimeMillis() - start;
                result.avgDuration = (result.totalDuration * 1.0) / consumingItemCount.get();
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
        long error;
        long success;
        double avgDuration;
        long totalDuration;
        long maxDuration = Long.MIN_VALUE;
        long minDuration = Long.MAX_VALUE;
    }

}