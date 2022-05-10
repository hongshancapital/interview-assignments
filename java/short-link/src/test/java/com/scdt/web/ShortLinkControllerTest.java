package com.scdt.web;

import com.scdt.ShortLinkApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * ShortLinkControllerTest
 *
 * @author weixiao
 * @date 2022-04-26 17:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShortLinkApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShortLinkControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testCreateShortLink() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/short-link?longLink=www.baidu.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertEquals("{\"success\":true,\"code\":0,\"msg\":\"成功\",\"data\":\"00000000\"}", result.getResponse().getContentAsString());
    }

    @Test
    public void testGetLongLink() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/long-link?shortLink=00000000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertEquals("{\"success\":true,\"code\":0,\"msg\":\"成功\",\"data\":\"www.baidu.com\"}", result.getResponse().getContentAsString());
    }
}
