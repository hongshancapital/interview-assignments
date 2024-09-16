package com.example.demo;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author shenbing
 * @since 2022/1/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestShortLink {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 短域名存储接口
     */
    @Test
    public void testRegister() throws Exception {
        JSONObject jsonObject = register();
        Assert.assertEquals(jsonObject.get("status"), 0);
    }

    private JSONObject register() throws Exception {
        String params = "{\"originalUrl\": \"http://www.baidu.com\"}";
        MvcResult result = mockMvc
                .perform(
                        post("/api/short/link/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(params)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        return new JSONObject(result.getResponse().getContentAsString());
    }

    /**
     * 短域名读取接口
     */
    @Test
    public void testGetOriUrl() throws Exception {
        getOriUrl("http://localhost:8081/BAA");
        getOriUrl("BAA");
    }

    private void getOriUrl(String shortUrl) throws Exception {
        testRegister();
        MvcResult result = mockMvc
                .perform(
                        get("/api/short/link/origin")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("shortUrl", shortUrl)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("status"), 0);
    }

    /**
     * 测试跳转
     * Redirected URL = http://www.baidu.com
     */
    @Test
    public void testRedirect() throws Exception {
        JSONObject jsonObject = register();
        JSONObject data = (JSONObject) jsonObject.get("data");
        String shortUrl = String.valueOf(data.get("shortUrl"));
        String shortStr = shortUrl.substring(shortUrl.lastIndexOf("/"));
        mockMvc
                .perform(get(shortStr).accept(MediaType.APPLICATION_JSON))
                .andExpect(header().stringValues("Location", "http://www.baidu.com"))
                .andDo(print())
                .andReturn();
    }

}
