package com.jk.shorturl.utils;

import com.jk.shorturl.Utils.LRUCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

class LRUCacheTest {

    @Test
    void TestRemove() {
        LRUCache<Integer, String> lru = new LRUCache<Integer, String>(1);
        //fillData(lru);
        lru.put(1, "1111");
        lru.put(11, null);

        System.out.println(lru.get(1));
        System.out.println(lru.toString());

        lru.put(3, "3333");
        System.out.println(lru.get(3));

        lru.put(4, "3333");

        System.out.println(lru.toString());
        System.out.println(lru.remove(11));
        System.out.println(lru.toString());
        System.out.println(lru.remove(11));
        System.out.println(lru.toString());

        System.out.println(lru.remove(3));
        System.out.println(lru.toString());


        System.out.println(lru.remove(3));
        System.out.println(lru.toString());
    }

    @Test
    void testTime() {
        LRUCache<String, String> lru = new LRUCache<String, String>(400000);
        long t0 = System.currentTimeMillis();
        fillData(lru);
        long t1 = System.currentTimeMillis();
        System.out.println("填充耗时：" + (t1 - t0));

        System.out.println(lru.getCurrentSize());
        System.out.println(lru.getMaxSize());

        lru.toString();
        //System.out.println(lru.toString());
        long t2 = System.currentTimeMillis();
        System.out.println("遍历耗时：" + (t2 - t1));

        System.out.println(lru.get("100"));
        long t3 = System.currentTimeMillis();
        System.out.println("获取头部耗时：" + (t3 - t2));

        System.out.println(lru.get("200000"));
        long t4 = System.currentTimeMillis();
        System.out.println("获取中间耗时：" + (t4 - t3));

        System.out.println(lru.get("390000"));
        long t5 = System.currentTimeMillis();
        System.out.println("获取尾部耗时：" + (t5 - t4));

        lru.clear();
        System.out.println(lru.getCurrentSize());
        System.out.println(lru.getMaxSize());
    }


    void fillData(LRUCache lruCache) {
        for (int i = 0; i < lruCache.getMaxSize() + 10; i++) {
            lruCache.put(i+"", (i * i)+"");
        }
    }

    @Test
    void testOrder() {
        LRUCache<Integer, String> lru = new LRUCache<Integer, String>(3);

        lru.put(1, "a");    // 1:a
        System.out.println(lru.toString());
        lru.put(2, "b");    // 2:b 1:a
        System.out.println(lru.toString());
        lru.put(3, "c");    // 3:c 2:b 1:a
        System.out.println(lru.toString());
        lru.put(4, "d");    // 4:d 3:c 2:b
        System.out.println(lru.toString());
        lru.put(1, "aa");   // 1:aa 4:d 3:c
        System.out.println(lru.toString());
        lru.put(2, "bb");   // 2:bb 1:aa 4:d
        System.out.println(lru.toString());
        lru.put(5, "e");    // 5:e 2:bb 1:aa
        System.out.println(lru.toString());
        lru.get(1);         // 1:aa 5:e 2:bb
        System.out.println(lru.toString());
        lru.remove(11);     // 1:aa 5:e 2:bb
        System.out.println(lru.toString());
        lru.remove(1);      //5:e 2:bb
        System.out.println(lru.toString());
        lru.put(1, "aaa");  //1:aaa 5:e 2:bb
        System.out.println(lru.toString());
    }
}
