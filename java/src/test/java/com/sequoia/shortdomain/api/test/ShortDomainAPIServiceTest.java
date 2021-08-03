package com.sequoia.shortdomain.api.test;

import com.sequoia.shortdomain.service.IShortDomainService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ShortDomainAPIServiceTest {
    @Autowired
    private IShortDomainService service;

    @Test
    public void testGetShortDomainByLongDomain(){
        String shortDomain=  service.getShortDomainByLongDomain("https://github.com/softprog/interview-assignments/tree/master/java");

        Assert.isTrue(StringUtils.isNotBlank(shortDomain),"ok");
    }
    @Test
    public void testGetShortDomainByLongDomainForCache(){
        String shortDomain=  service.getShortDomainByLongDomain("https://github.com/softprog/interview-assignments/tree/master/java");
        shortDomain=  service.getShortDomainByLongDomain("https://github.com/softprog/interview-assignments/tree/master/java");
        Assert.isTrue(StringUtils.isNotBlank(shortDomain),"ok");
    }
    @Test
    public void testGetLongDomainByLongDomain(){
        String shortDomain=  service.getShortDomainByLongDomain("https://github.com/softprog/interview-assignments/tree/master/java");

        String longDomain=service.getLongDomainByShortDomain(shortDomain);
        Assert.isTrue(StringUtils.isNotBlank(longDomain),"ok");
    }
}
