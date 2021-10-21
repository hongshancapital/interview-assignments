package com.shenshen.zhw.urlconverter.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
class LocalCacheTest {

    @Test
    void get() {
        LocalCache localCache = new LocalCache(10);
        localCache.put("k1","value1",1000L);
        localCache.put("k2","value2",2000L);
        localCache.put("k3","value3",3000L);

        Assert.isTrue(localCache.size()==3);
        System.out.println("check size ");

        try {
            Thread.sleep(1002L);
            Assert.isNull(localCache.get("k1"));
            System.out.println("k1 value is null ");


            Assert.isTrue("value2".equals(localCache.get("k2")) );
            System.out.println("k2 value is value2 ");


        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    @Test
    void put() {
        LocalCache localCache = new LocalCache(1);
        localCache.put("k1","value1",1000L);
        localCache.put("k2","value2",-1L);

        Assert.notNull(localCache.toString());


    }

    @Test
    void testToString() {
        LocalCache localCache = new LocalCache(10);
        localCache.put("k1","value1",1000L);

        Assert.notNull(localCache.toString(),"");
    }
}