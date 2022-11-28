package com.meihua.shorturl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.meihua.shorturl.common.dto.BaseResponse;
import com.meihua.shorturl.common.enums.ResponseCodeEnum;
import com.meihua.shorturl.controller.IDShortUrlController;
import com.meihua.shorturl.controller.Md5ShortUrlController;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ShortUrlApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlApplicationTests.class);

    private final static int  THREAD_NUM = 8;

    private final static int TASK_COUNT = 100000;

    @Autowired
    private IDShortUrlController idShortUrlController;

    @Autowired
    private Md5ShortUrlController md5ShortUrlController;

    public void idTask(){
        long l = System.currentTimeMillis();
        for (int j=0;j<TASK_COUNT;j++){
            StringBuilder url = new StringBuilder("https://www.baidu.com?time=");
            url.append(j);
            String resp = idShortUrlController.toShortUrl(url.toString()).getData();
            Assert.isTrue(!url.equals(idShortUrlController.getUrl(resp)),"数据不一致！");
            Assert.isTrue(resp.length()<=8,"长度超过限制！");
        }
        logger.info("耗时 ： {} ms",System.currentTimeMillis()-l);
    }

    public void md5Task(){
        long l = System.currentTimeMillis();
        for (int j=0;j<TASK_COUNT;j++){
            StringBuilder url = new StringBuilder("https://www.baidu.com?time=");
            url.append(j);
            String resp = md5ShortUrlController.toShortUrl(url.toString()).getData();
            Assert.isTrue(!url.equals(md5ShortUrlController.getUrl(resp)),"数据不一致！");
            Assert.isTrue(resp.length()<=8,"长度超过限制！");
        }
        logger.info("耗时 ： {} ms",System.currentTimeMillis()-l);
    }

    @Test
    void concurrentIdPerformanceTimeConsuming() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_NUM,THREAD_NUM,180, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(), new ThreadFactoryBuilder().setNameFormat("id-thread-%d").build());
        for (int i=0;i<THREAD_NUM;i++){
            pool.execute(()->{ idTask(); });
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    @Test
    void idPerformanceTimeConsuming() {
        long l = System.currentTimeMillis();
        for (int i=0;i<THREAD_NUM;i++){
            idTask();
        }
        logger.info("id 单线程 总耗时 ： {} ms",System.currentTimeMillis()-l);
    }


    @Test
    void concurrentMd5PerformanceTimeConsuming() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_NUM,THREAD_NUM,180, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(), new ThreadFactoryBuilder().setNameFormat("id-thread-%d").build());
        for (int i=0;i<THREAD_NUM;i++){
            pool.execute(()->{ md5Task(); });
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    @Test
    void md5PerformanceTimeConsuming() {
        long l = System.currentTimeMillis();
        for (int i=0;i<THREAD_NUM;i++){
            md5Task();
        }
        logger.info("md5 单线程 总耗时 ： {} ms",System.currentTimeMillis()-l);
    }


    @Test
    public void idErrorVerification(){
        long l = System.currentTimeMillis();
        for (int j=0;j<TASK_COUNT;j++){
            StringBuilder url = new StringBuilder("https://www.baidu.com?id=");
            url.append(UUID.randomUUID());
            String resp = idShortUrlController.toShortUrl(url.toString()).getData();
            Assert.isTrue(!url.equals(idShortUrlController.getUrl(resp)),"数据不一致！");
            Assert.isTrue(resp.length()<=8,"长度超过限制！");
           // logger.info(" url: {} resp : {} value:{} ",url,resp, idGeneratorShortUrl.getValue(resp));
        }
        logger.info(" id Generator Error Verification done !  耗时： {} ms",System.currentTimeMillis()-l);
    }

    @Test
    public void md5ErrorVerification(){
        long l = System.currentTimeMillis();
        for (int j=0;j<TASK_COUNT;j++){
            StringBuilder url = new StringBuilder("https://www.baidu.com?id=");
            url.append(UUID.randomUUID());
            String resp = md5ShortUrlController.toShortUrl(url.toString()).getData();
            Assert.isTrue(!url.equals(md5ShortUrlController.getUrl(resp)),"数据不一致！");
            Assert.isTrue(resp.length()<=8,"长度超过限制！");
            // logger.info(" url: {} resp : {} value:{} ",url,resp, idGeneratorShortUrl.getValue(resp));
        }
        logger.info(" id Generator Error Verification done !  耗时： {} ms",System.currentTimeMillis()-l);
    }



}
