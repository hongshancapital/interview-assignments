package com.rad.shortdomainname.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShortDomainServiceImplTest {
    @Resource
    private ShortDomainServiceImpl shortDomainService;

    @Test
    void generateShortLink() throws Exception {
        shortDomainService.generateShortLink("https://github.com/scdt-china/interview-assignments/tree/master/java");
    }
}