package com.wupo.junit.facade;

import com.wupo.exam.facade.DomainReflectFacade;
import com.wupo.junit.DomainBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

@Slf4j
public class DomainReflectFacadeTest extends DomainBaseTest {

    private static final int NUMBER = 4000;

    @Autowired
    private DomainReflectFacade domainReflectFacade;

    @Test
    public void regularTest(){
        String longUrl = "www.sequoiacap.cn/";
        String shortUrl = this.domainReflectFacade.saveLongDomain(longUrl);
        Assert.assertNotNull("域名保存失败，未返回短域名", shortUrl);
        Assert.assertTrue("域名映射失败",
                longUrl.equals(this.domainReflectFacade.getLongDomain(shortUrl)));
        String shortUrl2 = this.domainReflectFacade.saveLongDomain(longUrl);
        Assert.assertEquals("同域名重复映射", shortUrl, shortUrl2);
    }

    @Test
    public void exceptionTest(){
        String shortUrl = "www.sequoiacap.cn/";
        Assert.assertEquals("域名映射失败",
                DomainReflectFacade.NOT_FOUNT_LONG_DOMAIN,
                this.domainReflectFacade.getLongDomain(shortUrl));
    }

    @Test
    public void performanceTest() throws Exception{
        Set<String> shortUrlSet = Collections.synchronizedSet(new HashSet<>());
        List<Long> timeCostList = Collections.synchronizedList(new ArrayList<>());
        String longUrl = "www.sequoiacap.cn/";
        CountDownLatch latch = new CountDownLatch(NUMBER);
        CountDownLatch mainLatch = new CountDownLatch(NUMBER);
        for(int i = 0; i < NUMBER; i++){
            FutureTask<Long> task = new FutureTask<>(
                    new UrlCallable(this.domainReflectFacade, longUrl + i,
                            shortUrlSet, latch, mainLatch, timeCostList));
            new Thread(task).start();
        }
        try {
            mainLatch.await();
        }catch (Exception e){
            log.error("闭锁等待释放失败", e);
            throw e;
        }
        Long maxCost = 0L;
        for(Long cost : timeCostList){
            maxCost = Math.max(maxCost, cost);
        }
        log.info("单个请求最长处理时间为{}毫秒", maxCost);
        int s = shortUrlSet.size();
        Assert.assertEquals("返回的短域名数量不足，共返回" + s + "个短域名", NUMBER, s);
    }

}
