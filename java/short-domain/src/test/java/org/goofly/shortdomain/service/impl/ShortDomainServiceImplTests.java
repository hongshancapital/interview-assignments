package org.goofly.shortdomain.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest()
public class ShortDomainServiceImplTests {
    @Resource
    private ShortDomainServiceImpl shortDomainService;

    @Test
    public void testConvertShortDomain() {
        String url = "https://segmentfault.com/a/1190000017412946";
        Assert.assertNotNull(shortDomainService.convertShortDomain(url));
        Assert.assertNull(shortDomainService.convertOriginalDomain("qqqqqqqqq"));

        Assert.assertThrows(IllegalArgumentException.class, () -> shortDomainService.convertShortDomain(null));

    }

    @Test
    public void testConvertShortCode() {
        String url = "https://segmentfault.com/a/1190000017412946";
        String shortDomain = shortDomainService.convertShortDomain(url);

        Assert.assertEquals(url, shortDomainService.convertOriginalDomain(shortDomain));
    }
}
