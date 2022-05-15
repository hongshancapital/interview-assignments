package com.scdt.china.shortdomain.controller;

import com.scdt.china.shortdomain.utils.Result;
import com.scdt.china.shortdomain.utils.ReturnCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: CZ
 * @Date: 2022/1/23 14:34
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainControllerTest {

    @Autowired
    private DomainController domainController;
    private final String longDomain = "http://localhost:8080/getLongDomain?shortDomain=1a2b3c4e";

    @Test
    public void hello() {
        String param = "World";
        String result = domainController.hello(param);
        Assert.assertNotEquals(param, result);
    }

    @Test
    public void getShortDomain() {
        Result<String> shortDomainResult = domainController.getShortDomain(longDomain);
        Assert.assertNotNull(shortDomainResult);
        Assert.assertTrue(shortDomainResult.isSuccess());
        Assert.assertNotNull(shortDomainResult.getData());

        shortDomainResult = domainController.getShortDomain("");
        Assert.assertNotNull(shortDomainResult);
        Assert.assertFalse(shortDomainResult.isSuccess());
        Assert.assertEquals(shortDomainResult.getCode(), ReturnCode.PARAM_ERROR.getCode());
    }

    @Test
    public void getLongDomain() {
        Result<String> shortDomainResult = domainController.getShortDomain(longDomain);
        Assert.assertNotNull(shortDomainResult);
        Assert.assertTrue(shortDomainResult.isSuccess());
        String shortDomain = shortDomainResult.getData();

        Result<String> longDomainResult = domainController.getLongDomain(shortDomain);
        Assert.assertNotNull(longDomainResult);
        Assert.assertTrue(longDomainResult.isSuccess());
        Assert.assertEquals(longDomain, longDomainResult.getData());

        longDomainResult = domainController.getLongDomain("");
        Assert.assertNotNull(longDomainResult);
        Assert.assertFalse(longDomainResult.isSuccess());
        Assert.assertEquals(longDomainResult.getCode(), ReturnCode.PARAM_ERROR.getCode());

        longDomainResult = domainController.getLongDomain(shortDomain + shortDomain);
        Assert.assertNotNull(longDomainResult);
        Assert.assertFalse(longDomainResult.isSuccess());
    }
}