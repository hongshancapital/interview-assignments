package com.example.shortlink;


import com.example.shortlink.service.ShortLongLinkService;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j2
@SpringBootTest
public class MultiThreadTest {


    @Autowired
    private ShortLongLinkService service ;


    private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String URL_SUFFIX = "http://www.hyena.com/";

    private static final int MAX_URL_LENGTH = 8 ; // assignment required.


    private static final Random rn = new Random();




    private String getRandomLongLinkUrl() {


        Math.random();

        String url = "";
        for (int i = 0; i < MAX_URL_LENGTH ; i++) {
            url += BASE.charAt(rn.nextInt(62));
        }



        return URL_SUFFIX + url ;
    }


    private class TestUnit extends Thread{


        private int threadcount ;

        private int taskPerThread;

        private int taskCount;


        private AtomicInteger failedCount ;


        private AtomicInteger succeedCount ;


        private AtomicLong totalTimeSpent ;





        private CountDownLatch latch = new CountDownLatch(threadcount);


        public TestUnit(int threadCount , int taskPerThread ){
            this.threadcount = threadCount;
            this.taskPerThread = taskPerThread;
            this.taskCount = threadCount * taskPerThread;
            this.failedCount = new AtomicInteger(0);
            this.succeedCount = new AtomicInteger(0);
            this.totalTimeSpent = new AtomicLong(0L);


        }


        @Override
        public void run() {
            for (int i = 0; i < threadcount; i++) {
                Thread t = new Thread(this::doRun);
                t.start();
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // log result
            log.info("threadCount:{} ,task/Thread:{} ,taskCount:{} ,succeedCount:{}, failedCount:{} ,totalTimeSpent:{}ms ," +
                    "avgTimeSpent:{}ms",threadcount,taskPerThread,taskCount,succeedCount.get(),failedCount.get(),totalTimeSpent.get(),
                    new BigDecimal(totalTimeSpent.get()).divide(new BigDecimal(taskCount),6, RoundingMode.HALF_UP));


        }

        private void doRun() {

            Long start = System.currentTimeMillis();

            for (int i = 0; i < taskPerThread; i++) {
                try {
                    String longLink = getRandomLongLinkUrl();
                    String shortLink = service.acquireShortLink(longLink);
                    String result = service.acquireLongLink(shortLink);
                    Assert.assertEquals(result, longLink);
                    succeedCount.incrementAndGet();
                } catch (Exception e) {
                    log.error("Task error",e);
                    failedCount.incrementAndGet();
                }finally {
                    latch.countDown();
                }
            }

            totalTimeSpent.addAndGet(System.currentTimeMillis() -start);

        }
    }





    /**
     * multi-thread concurrent long - short test.
     *
     * Thread : 1000.
     * task/per thread : 10
     *
     * count     succeed       failed
     *
     *
     * result :
     *
     *  threadCount:1000 ,task/Thread:20 ,taskCount:20000 ,succeedCount:19994, failedCount:0 ,totalTimeSpent:3611ms ,avgTimeSpent:0.180550ms
     *  threadCount:5000 ,task/Thread:20 ,taskCount:100000 ,succeedCount:100000, failedCount:0 ,totalTimeSpent:1917ms ,avgTimeSpent:0.019170ms
     *  threadCount:10000 ,task/Thread:20 ,taskCount:200000 ,succeedCount:200000, failedCount:0 ,totalTimeSpent:2744ms ,avgTimeSpent:0.013720ms
     *  threadCount:15000 ,task/Thread:20 ,taskCount:300000 ,succeedCount:299970, failedCount:0 ,totalTimeSpent:2601ms ,avgTimeSpent:0.008670ms
     *  threadCount:20000 ,task/Thread:20 ,taskCount:400000 ,succeedCount:399961, failedCount:0 ,totalTimeSpent:6405ms ,avgTimeSpent:0.016013ms
     *  threadCount:25000 ,task/Thread:20 ,taskCount:500000 ,succeedCount:499980, failedCount:0 ,totalTimeSpent:2857ms ,avgTimeSpent:0.005714ms
     */
    @Test
    public void multiThreadTest() throws InterruptedException {
        Thread t1 = new TestUnit(1000,20);
        Thread t2 = new TestUnit(5000,20);
        Thread t3 = new TestUnit(10000,20);
        Thread t4 = new TestUnit(15000,20);
        Thread t5 = new TestUnit(20000,20);
        Thread t6 = new TestUnit(25000,20);
        Thread t7 = new TestUnit(30000,20);


        t1.start();t1.join();
        t2.start();t2.join();
        t3.start();t3.join();
        t4.start();t4.join();
        t5.start();t5.join();
        t6.start();t6.join();
        t7.start();t6.join();

    }





}
