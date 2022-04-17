package com.lisi.urlconverter.tool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @description: ConcurrentLinkedQueue性能测试,测试高并发下写入数据的效率
 * @author: li si
 */

public class TestConcurrentLinkedQueue implements Runnable {
    // 并发线程数
    private static final int THREAD_NUMS = 200;

    // 每线程向队列中添加的数量
    private static final int ADD_NUM_PER_THREAD = 10000;

    private CountDownLatch countDownLatch;
    private ConcurrentLinkedQueue<Long> queue;

    public TestConcurrentLinkedQueue(CountDownLatch countDownLatch, ConcurrentLinkedQueue queue) {
        this.countDownLatch = countDownLatch;
        this.queue = queue;
    }

    public static void main(String[] args) throws Exception {
        ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUMS);
        long start = System.currentTimeMillis();
        for(int i = 0; i < THREAD_NUMS; i++){
            Thread thread = new Thread(new TestConcurrentLinkedQueue(countDownLatch, queue));
            thread.start();
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("cost : " + (end - start));
        System.out.println(queue.size());
    }

    @Override
    public void run() {
        for(int i = 0; i < ADD_NUM_PER_THREAD; i++){
            this.queue.offer(System.currentTimeMillis());
        }
        countDownLatch.countDown();
    }
}
