package com.wangxingchao.shorturl.controller;

import com.wangxingchao.shorturl.utils.Result;
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

    /**
     * 长域名转短域名测试
     */
    @Test
    public void long2short() {
        Result aShort = urlController.long2short("ABC");
        System.out.println(aShort);
        Object data = aShort.getData();
        if (data != null) {
            Result aLong = urlController.short2long(data.toString());
            System.out.println(aLong);
        }
    }

}
