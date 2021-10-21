package com.shenshen.zhw.urlconverter.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ConvertServiceImplTest {

    @Test
    void save() {
        ConvertServiceImpl impl = new ConvertServiceImpl();
        Assert.isNull(impl.save(""));
        Assert.isNull(impl.save(null));


        String url = "http://www.google.com";
        String sId=  impl.save(url);
        System.out.println("sId="+sId);

        String lId = impl.get(sId);
        System.out.println("lId = "+lId);
        Assert.isTrue(url.equals(lId));

    }

    @Test
    void get() {
        ConvertServiceImpl impl = new ConvertServiceImpl();
        Assert.isNull(impl.get(""));
        Assert.isNull(impl.get(null));
    }

    @Test
    void performance(){
        ConvertServiceImpl impl = new ConvertServiceImpl();
        //系统最大缓存1万
        //10个线程，测试5000个保存, 50ms
        //10个线程，测试10000个保存, 75ms
        //10个线程，测试50000个保存, 181ms
        //10个线程，测试100000个保存, 255ms
        //10个线程，测试200000个保存, 1423ms
        //查询就是LinkedHashMap的性能

        int max=500000;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(4,4,0, TimeUnit.SECONDS,new LinkedBlockingQueue<>(max));

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    impl.save("http://url"); //因为短域名标记和域名无关，用固定值即可
                }
            });
        }
        pool.shutdown();
        try{
            pool.awaitTermination(Integer.MAX_VALUE,TimeUnit.SECONDS);
        }catch (Exception ex){
            System.out.println(ex);
        }

        System.out.println("save（"+max+"） time = "+(System.currentTimeMillis()-t1));

    }
}