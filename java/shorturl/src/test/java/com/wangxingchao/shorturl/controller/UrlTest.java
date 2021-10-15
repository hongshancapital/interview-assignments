package com.wangxingchao.shorturl.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlTest {

    @Resource
    private UrlController urlController;

    @Test
    public void long2short() {
        String aShort = urlController.long2short("ABC");
        System.out.println(aShort);
        String aLong = urlController.short2long(aShort);
        System.out.println(aLong);
    }

}
