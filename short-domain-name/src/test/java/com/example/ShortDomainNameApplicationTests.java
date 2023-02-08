package com.example;

import com.example.controller.DomainNameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortDomainNameApplicationTests {
    private static String domain = null;
    @Autowired
    private DomainNameController domainNameController;

    @Test
    String getShortAccordingToLong() throws Exception {
        String longDomain = null;
        try {
            domainNameController.getShortAccordingToLong(longDomain);
            longDomain = "";
            domainNameController.getShortAccordingToLong(longDomain);
        } catch (Exception e) {
            // 因参数为空导致得返回错误
        }
        longDomain = "http://www.baidu.com";
        domainNameController.getShortAccordingToLong(longDomain);
        domainNameController.getShortAccordingToLong(longDomain);
        for (int i = 0; i < 10; i++) {
            domainNameController.getShortAccordingToLong(longDomain+i);
        }
        for (int i = 0; i < 10; i++) {
            domainNameController.getShortAccordingToLong(longDomain+i);
        }
        longDomain = "http://www.baidu.com5";
        String shortAccordingToLong = domainNameController.getShortAccordingToLong(longDomain);
        domain = shortAccordingToLong;
        System.out.println(domain);
        return shortAccordingToLong;
    }

    @Test
    String shortDomainNameToGetLongDomainName() {
        String domainTest = null;
        // 通过短域名查询到长域名
        domainNameController.shortDomainNameToGetLongDomainName(domainTest);
        domainTest = "";
        domainNameController.shortDomainNameToGetLongDomainName(domainTest);
        String longDomain = domainNameController.shortDomainNameToGetLongDomainName(domain);
        System.out.println(longDomain);
        return longDomain;
    }

    @Test
    void contextLoads() throws Exception {
        this.getShortAccordingToLong();
        this.shortDomainNameToGetLongDomainName();
    }


}
