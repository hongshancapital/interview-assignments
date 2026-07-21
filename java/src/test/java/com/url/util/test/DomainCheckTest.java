package com.url.util.test;

import com.url.Application;
import com.url.utils.DomainCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 域名格式检查测试类
 * @ClassName DomainCheckTest
 * @Author abc
 * @Date 2022/4/30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class DomainCheckTest {

    @Test
    public void domainCheckTrueTest(){
        String url = "https://www.baidu.com/test";
        boolean res = DomainCheckUtil.check(url);
        log.info("domain check test result:{}",res);
        Assert.assertEquals(res,true);
    }

    @Test
    public void domainCheckFalseTest(){
        String url = "234";
        boolean res = DomainCheckUtil.check(url);
        log.info("domain check test result:{}",res);
        Assert.assertEquals(res,false);
    }
}