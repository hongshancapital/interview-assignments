package com.wupo.junit.facade;

import com.wupo.exam.facade.DomainReflectFacade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
class UrlCallable implements Callable<Long> {

    private DomainReflectFacade domainReflectFacade;

    private String longUrl;

    private Set<String> shortUrlSet;

    private CountDownLatch latch;

    private CountDownLatch mainLatch;

    private List<Long> timeCostList;

    @Override
    public Long call() throws Exception {
        Long cost;
        try {
            latch.countDown();
            log.info("----等待");
            latch.await();
            long start = System.currentTimeMillis();
            String shortUrl = this.domainReflectFacade.saveLongDomain(longUrl);
            log.info(shortUrl);
            shortUrlSet.add(shortUrl);
            cost = System.currentTimeMillis() - start;
            timeCostList.add(cost);
            mainLatch.countDown();
        }catch (Exception e){
            log.error("------性能测试失败，请检查------", e);
            throw e;
        }
        return cost;
    }
}
