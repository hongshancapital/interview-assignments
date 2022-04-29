package com.hszb.shorturl;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/20 2:06 下午
 * @Version: 1.0
 * @Description:
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTestSupport {

    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext webApplicationConnect;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
    }

    public MockHttpServletRequestBuilder post(String uri) {
        return MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON);
    }

    public MockHttpServletRequestBuilder get(String uri) {
        return MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
    }

    public int executeTest(Object obj, String url) throws Exception {
        RequestBuilder request = post(url).contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(obj));
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        return mvcResult.getResponse().getStatus();
    }
}
