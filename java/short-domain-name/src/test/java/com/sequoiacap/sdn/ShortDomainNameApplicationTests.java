package com.sequoiacap.sdn;

import com.sequoiacap.sdn.service.impl.DomainServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class ShortDomainNameApplicationTests {

    @Autowired
    private DomainServiceImpl domainService;

    @BeforeEach
    void setUp() {
        System.out.println("域名转换测试开始");
    }

    @AfterEach
    void tearDown() {
        System.out.println("域名转换日志测试结束");
    }

    /**
     * 测试长域名转短域名
     */
    @Test
    void langToShort() throws Exception{
        ArrayList<String> list = new ArrayList<>();
        list.add("https://www.google.com");
        list.add("https://www.baidu.com");
        list.add("https://www.alibaba.com");
        list.add("https://www.tencent.com");
        for (String s : list) {
            String aShort = domainService.langToShort(s);
            System.out.println(aShort);
        }
    }

    /**
     * 测试存储后再通过短域名获取长域名
     */
    @Test
    void getLong() throws Exception{
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> listTemp = new ArrayList<>();
        list.add("https://www.google.com");
        list.add("https://www.baidu.com");
        list.add("https://www.alibaba.com");
        list.add("https://www.tencent.com");
        //先存储
        for (String s : list) {
            String aShort = domainService.langToShort(s);
            System.out.println(aShort);
            listTemp.add(domainService.langToShort(s));
        }
        //再取值
        for (String s : listTemp) {
            String aLong = domainService.getLang(s);
            System.out.println(aLong);
        }
    }


}
