package com.shorturl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by ruohanpan on 21/3/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Tester {

    @Autowired
    private RedissonClient client;

    @Before
    public void init() {

    }

    @Test
    public void test() throws InterruptedException {
        RLock lock = client.getLock("initial_code_in_use_lock");
        lock.tryLock(1, TimeUnit.SECONDS);
        lock.unlock();

        System.out.println(Thread.currentThread().getName() + " is shutdown: " + client.isShutdown());

        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RLock rLock = client.getLock("initial_code_in_use_lock");
                    System.out.println(Thread.currentThread().getName() + " is shutdown: " + client.isShutdown());
                    try {
//                        Thread.sleep(1000);
                        rLock.tryLock(5, 30, TimeUnit.SECONDS);
                        System.out.println(Thread.currentThread() + " - acquired lock");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        rLock.unlock();
                    }
                }
            }).start();
        }
    }
}
