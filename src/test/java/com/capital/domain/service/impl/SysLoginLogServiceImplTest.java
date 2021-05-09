package com.capital.domain.service.impl;


import com.capital.domain.BaseApplicationTest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jiangts
 * @Vsersion V1.0
 */
class SysLoginLogServiceImplTest extends BaseApplicationTest {

    @Autowired
    private ConversionServiceImpl conversionService;
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
        list.add("qfq2f23rr3fwrwfawr");
        list.add("wf4t3g45ye5y4wye4");
        list.add("f25yt4y4eyhsrrgsgsg");
        list.add("d6u845uj5jr65h5y6he5");
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String aShort = conversionService.langToShort(s);
            Assert.assertEquals(aShort,String.valueOf(i));
        }
    }

    /**
     * 测试存储后再通过短域名获取长域名
     */
    @Test
    void getLong() throws Exception{
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> listTemp = new ArrayList<>();
        list.add("qfq2f23rr3fwrwfawr");
        list.add("wf4t3g45ye5y4wye4");
        list.add("f25yt4y4eyhsrrgsgsg");
        list.add("d6u845uj5jr65h5y6he5");
        //先存储
        for (String s : list) {
            listTemp.add(conversionService.langToShort(s));
        }
        //再取值
        for (String s : listTemp) {
            String aLong = conversionService.getLang(s);
            System.out.println(aLong);
        }
    }

    public interface Test1{
        Integer method(int i);
        Integer method2(int i);
    }
    @Test
    void login(List list) {
    }

    @Test
    void logout() {
    }
}