package com.redwood.shorturl.test.inter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author 龙小虬
 * @since 2020-07-08 21:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseJunit {

    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    /**
     * 初始化SpringmvcController类测试环境
     */
    @Before
    public void setup(){
        //加载web容器上下文
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}

