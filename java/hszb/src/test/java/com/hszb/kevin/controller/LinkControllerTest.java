package com.hszb.kevin.controller;

import com.hszb.kevin.HomeworkApplication;
import com.hszb.kevin.dto.LinkDto;
import com.hszb.kevin.response.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.net.URL;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = HomeworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LinkControllerTest {


    private String longLink = "https://blog.csdn.net/xxx/article/details/107822676";

    private String shortLink = "";

    Logger logger = LoggerFactory.getLogger(LinkControllerTest.class);

    @Autowired
    private LinkController linkController;

    

    @Test
    public void test1() {
        R<LinkDto> r =  linkController.getShortLink(longLink);
        shortLink = r.getData().getShortLink();
        logger.info("长链转短链接口测试返回结果："+shortLink);
        R<LinkDto> r1 =  linkController.getLongLink(shortLink);
        if(r.getCode() == 200) {
            longLink = r1.getData().getLongLink();
        }
        logger.info("短链转长链接接口测试返回结果：" + longLink);
    }

    @Test
    public void test2() {
        R<LinkDto> r =  linkController.getLongLink(shortLink);
        if(r.getCode() == 200) {
            longLink = r.getData().getLongLink();
        }
        logger.info("短链转长链接接口测试返回结果：" + longLink);
    }

    @Test
    public void test3() {
        String url = "https://blog.csdn.net/xxx/article/details/1078226761";
        R<LinkDto> r =  linkController.getShortLink(url);
        shortLink = r.getData().getShortLink();
        logger.info("长链转短链接口测试返回结果："+shortLink);
    }

    @Test
    public void test4() {
        R<LinkDto> r =  linkController.getShortLink(longLink);
        shortLink = r.getData().getShortLink();
        logger.info("长链转短链接口测试返回结果："+shortLink);
    }

    @Test
    public void test5() {
        R<LinkDto> r =  linkController.getLongLink("http://test");
        if(r.getCode() == 200) {
            longLink = r.getData().getLongLink();
        }else {
            longLink = "该短链接不存在";
        }
        logger.info("短链转长链接接口测试返回结果：" + longLink);
    }

}
