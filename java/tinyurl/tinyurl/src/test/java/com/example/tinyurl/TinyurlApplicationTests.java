package com.example.tinyurl;

import com.example.tinyurl.controller.TinyURLController;
import com.example.tinyurl.util.LRU;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
class TinyurlApplicationTests {

    @Autowired
    TinyURLController tinyURLController;

    String defaultUrl = "www.baidu.com";
    String url = "https://sports.sina.com.cn/g/pl/2021-12-17/doc-ikyamrmy9459975.shtml";

    static final int threadSize = 10;//线程数量

    @Test
    void testController() {

        /**
         * 测试生成的短链接是否小于8位
         */
        String tinyurl = tinyURLController.toTinyurl(url);
        Assert.assertTrue(tinyurl.length() <= 8);

        /**
         * 测试使用短链接获取的长链接是否正确
         */
        String longurl = tinyURLController.fromTinyurl(tinyurl);
        Assert.assertEquals(url, longurl);

        /**
         * 测试使用不存在的短链接获取的长链接是否跳转默认链接
         */
        longurl = tinyURLController.fromTinyurl("xxxxx");
        Assert.assertEquals(defaultUrl, longurl);
    }

    /**
     * 1.测试多线程安全
     * 2.测试数据远超缓存最大数据的时候，缓存的数量，是否内存溢出
     */
    @Test
    void testMemory() {

        int totalSize = (int)(Math.pow(2,20));//缓存总容量
        AtomicInteger size = new AtomicInteger();
        for(int i =0; i < threadSize; i++) {
            int finalI = i;
            new Thread(() -> {
                StringBuffer stringBuffer;
                for (int j = 0; j < totalSize; j++) {
                    stringBuffer = new StringBuffer();
                    stringBuffer.append(url);
                    stringBuffer.append("_");
                    stringBuffer.append(finalI);
                    stringBuffer.append("_");
                    stringBuffer.append(j);
                    tinyURLController.toTinyurl(stringBuffer.toString());
                }
                size.getAndIncrement();
            }).start();
        }

        //阻塞，直到线程都跑完
        while(size.get() < threadSize) {

        }

        Assert.assertTrue(LRU.getInstance().getCurrentSize() == totalSize);
    }

}
