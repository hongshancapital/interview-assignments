package com.hongshang;

import mockit.Expectations;
import mockit.Tested;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.Assert.*;

/**
 * spriingBoot启动类测试
 *
 * @author kobe
 * @date 2021/12/19
 */
public class ShorturlwebApplicationTest {

    @Tested
    ShorturlwebApplication shorturlwebApplication;

    /**
     * 测试启动方法
     */
    @Test
    public void mainTest() {
        String[] args = null;
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext();
        //mock静态方法，所以这里可以用类对象
        new Expectations(SpringApplication.class)
        {
            {
                SpringApplication.run(ShorturlwebApplication.class,args);
                result = context;
            }
        };

        shorturlwebApplication.main(args);
    }
}