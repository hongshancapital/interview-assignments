package com.shenshen.zhw.urlconverter.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.*;

class SnowFlakeTest {

    @Test
    void nextId() {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        ExecutorService es= Executors.newFixedThreadPool(10);


        ConcurrentHashMap<Long,Long> hashMap = new ConcurrentHashMap<>(10000);
       // Set<Long> list = new HashSet<>(10000);

        for (int i = 0; i < 6000; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    hashMap.put(snowFlake.nextId(),snowFlake.nextId());
                }
            });

        }
        es.shutdown();
        try {
            es.awaitTermination(60,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.isTrue(hashMap.size()==6000);
    }

    @Test
    void getNextMill() throws Exception{
        SnowFlake snowFlake = new SnowFlake(2, 3);
        long l= snowFlake.getNextMill();
        Assert.isTrue(l>0);


        try{
            snowFlake = new SnowFlake(64, 3);
        }catch (Exception ex){
            System.out.println("datacenterId test is ok");
        }
        try{
            snowFlake = new SnowFlake(8, 64);
        }catch (Exception ex){
            System.out.println("machineId test is ok");
        }


    }
}