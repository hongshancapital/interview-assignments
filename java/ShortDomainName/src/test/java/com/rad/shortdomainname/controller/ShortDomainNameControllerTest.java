package com.rad.shortdomainname.controller;

import com.rad.shortdomainname.common.Result;
import com.rad.shortdomainname.service.impl.SnowFlakeServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
class ShortDomainNameControllerTest {

    @Resource
    private ShortDomainNameController shortDomainNameController;

    @Resource
    private SnowFlakeServiceImpl snowFlakeService;

    @Test
    public void testFindLongLink() {
        // key不为空
        try {
            shortDomainNameController.getLongDomain("https://github.com/scdt-china/interview-assignments/tree/master/java");
        } catch (Exception e) {
            Assertions.assertEquals("短链接不合法", e.getMessage());
        }

        // key为空
        try {
            shortDomainNameController.getLongDomain("");
        } catch (Exception e) {
            Assertions.assertEquals("短链接不合法", e.getMessage());
        }

        String longLink = "https://www.baidu.com";
        Result shortResult = shortDomainNameController.getShortDomain(longLink);
        String shortLink = String.valueOf(shortResult.getData());
        Result longResult = shortDomainNameController.getLongDomain(String.valueOf(shortLink));
        Assert.assertEquals(String.valueOf(longResult.getData()), longLink);
    }

    @Test
    public void testGenerateShortLink() {
        shortDomainNameController.getShortDomain("https://www.baidu.com");

        for (int i = 0; i < 350; i++) {
            snowFlakeService.generateId();
            try {
                shortDomainNameController.getShortDomain("https://github.com/scdt-china/interview-assignments/tree/master/java");
            } catch (Exception e) {
                Assertions.assertEquals("号码已经用完", e.getMessage());
            }
        }
    }
}