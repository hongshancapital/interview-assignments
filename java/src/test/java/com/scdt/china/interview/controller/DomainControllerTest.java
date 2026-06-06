package com.scdt.china.interview.controller;

import com.scdt.china.interview.AbstractTest;
import com.scdt.china.interview.DataFile;
import com.scdt.china.interview.cache.DomainCache;
import com.scdt.china.interview.enm.ResultEnum;
import com.scdt.china.interview.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sujiale
 *
 */
public class DomainControllerTest extends AbstractTest {

    @Autowired
    private DomainController domainController;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public DomainControllerTest() {
    }

    @Test(dataProvider = "json")
    @DataFile(path = "data/domain/longDomain.json")
    public void testDomainController(List<String> shortDomains1, List<String> shortDomains2,
            List<String> shortDomains3) {
        //获取短域名
        shortDomains1.forEach(f -> {
            final Result<String> shortDomain = domainController.getShortDomain(f);
            Assert.assertNotNull(shortDomain.getData());
        });
        Assert.assertEquals(DomainCache.SHORT_LONG.size(), DomainCache.LONG_SHORT.size());
        Assert.assertEquals(DomainCache.SHORT_LONG.size(), 3);
        for (int i = 0; i < 5; i++) {
            //并发测试
            executorService.submit(() -> shortDomains2.forEach(f -> {
                final Result<String> shortDomain = domainController.getShortDomain(f);
                Assert.assertNotNull(shortDomain.getData());
            }));
            executorService.submit(() -> shortDomains3.forEach(f -> {
                final Result<String> shortDomain = domainController.getShortDomain(f);
                Assert.assertNotNull(shortDomain.getData());
            }));
        }
    }

    @Test(dependsOnMethods = { "testDomainController" })
    public void testGetLongDomain() {
        final Set<String> keys = DomainCache.SHORT_LONG.keySet();
        keys.forEach(f -> {
            final String longDomain = domainController.getLongDomain(f).getData();
            Assert.assertEquals(f, domainController.getShortDomain(longDomain).getData());
        });
        final Result<String> result = domainController.getLongDomain("123456789");
        Assert.assertEquals(result.getCode(), ResultEnum.E000001.getCode());
        Assert.assertEquals(result.getMsg(), ResultEnum.E000001.getMessage());
    }
}