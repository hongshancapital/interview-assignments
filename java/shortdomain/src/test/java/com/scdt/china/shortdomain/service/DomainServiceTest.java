package com.scdt.china.shortdomain.service;

import com.scdt.china.shortdomain.utils.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: CZ
 * @Date: 2022/1/22 20:34
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainServiceTest {
    @Autowired
    private DomainService domainService;
    private final String longDomain = "http://localhost:8080/getLongDomain?shortDomain=1a2b3c4e";

    @Test
    public void getShortDomain() {
        Result<String> shortDomainResult = domainService.getShortDomain(longDomain);
        Assert.assertNotNull(shortDomainResult);
        Assert.assertTrue(shortDomainResult.isSuccess());
        Assert.assertNotNull(shortDomainResult.getData());
    }

    @Test
    public void getLongDomain() {
        Result<String> shortDomainResult = domainService.getShortDomain(longDomain);
        Assert.assertNotNull(shortDomainResult);
        Assert.assertTrue(shortDomainResult.isSuccess());
        String shortDomain = shortDomainResult.getData();

        Result<String> longDomainResult = domainService.getLongDomain(shortDomain);
        Assert.assertNotNull(longDomainResult);
        Assert.assertTrue(longDomainResult.isSuccess());
        Assert.assertEquals(longDomain, longDomainResult.getData());

        longDomainResult = domainService.getLongDomain(shortDomain + shortDomain);
        Assert.assertNotNull(longDomainResult);
        Assert.assertFalse(longDomainResult.isSuccess());
    }
}