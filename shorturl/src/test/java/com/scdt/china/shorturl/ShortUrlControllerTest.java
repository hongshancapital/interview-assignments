package com.scdt.china.shorturl;

import com.scdt.china.shorturl.bootstrap.ShortUrlApplication;
import com.scdt.china.shorturl.web.exceptionhandler.GlobalExceptionHandler;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = MOCK,
        classes = ShortUrlApplication.class)
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testGetShortUrl() throws Exception {
        MvcResult authResult = mvc.perform(get("/getShortUrl?url=www.baidu.com")
                .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE)).andReturn();

        Assert.assertTrue(authResult.getResponse().getContentAsString().length() < 8);
    }

    @Test
    public void testGetUrl() throws Exception {
        MvcResult authResult = mvc.perform(get("/getShortUrl?url=www.baidu.com")
                .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE)).andReturn();

        authResult = mvc.perform(get("/getUrl?shortUrl=" + authResult.getResponse().getContentAsString())
                .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE)).andReturn();

        Assert.assertTrue(authResult.getResponse().getContentAsString().equals("www.baidu.com"));
    }

    @Test
    public void testException() throws Exception {
        MvcResult authResult = mvc.perform(get("/getShortUrl?")
                .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE)).andReturn();
        Assert.assertTrue(authResult.getResponse().getContentAsString().contains("can not be empty"));
    }

}
