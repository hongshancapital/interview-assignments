package com.hongshang.common;

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;

/**
 * 动态获取spring中bean的工具类测试
 *
 * @author kobe
 * @date 2021/12/19
 */
@RunWith(JMockit.class)
public class BaseHolderTest {

    @Tested
    BaseHolder baseHolder;

    @Injectable
    ApplicationContext ctx;

    @Test
    public void testSetApplicationContext() {
        baseHolder.setApplicationContext(ctx);
    }

    @Test
    public void testGetApplicationContext() {
        baseHolder.getApplicationContext();
    }

    @Test
    public void getBean() {
        new Expectations() {
            {
                ctx.getBean("baseHolder");
                result = new BaseHolder();
            }
        };
        baseHolder.setApplicationContext(ctx);
        BaseHolder baseH = baseHolder.getBean("baseHolder");
        assertNotNull(baseH);
    }

    @Test
    public void testGetBean() {
        new Expectations() {
            {
                ctx.getBean(BaseHolder.class);
                result = new BaseHolder();
            }
        };
        baseHolder.setApplicationContext(ctx);
        BaseHolder baseH = baseHolder.getBean(BaseHolder.class);
        assertNotNull(baseH);
    }
}