package com.example.control;

import com.example.service.ShortUrlCreateService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springfox.documentation.spring.web.json.Json;

/**
 * 单元测试demo
 *
 * @author wenbin
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DemoControllerTest {


    @Autowired
    private DemoController demoController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(demoController).build();
    }

    @Test
    public void testCrtShortUrl() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/demo/crtShortUrl")
                        .param("lurl", "www.baidu.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        //断言，判断返回代码是否正确
        Assert.assertEquals(200, status);
        //断言，判断返回的值是否正确
        Assert.assertTrue(content.contains("shortUrl"));
    }

    @Test
    public void testCrtShortUrlNullParam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/demo/crtShortUrl")
                        .param("lurl", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        //断言，判断返回代码是否正确
        Assert.assertEquals(200, status);
        //断言，判断返回的值是否正确
        Assert.assertTrue(content.contains("002"));
    }


    @Test
    public void testQueryLurlBySurl() throws Exception {
        MvcResult saveResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/demo/crtShortUrl")
                        .param("lurl", "www.baidu.com")
                        .accept(MediaType.APPLICATION_JSON)).andReturn();
        //得到返回代码
        int status = saveResult.getResponse().getStatus();
        //得到返回结果
        String content = saveResult.getResponse().getContentAsString();

        //断言，判断返回代码是否正确
        Assert.assertEquals(200, status);
        if(content.contains("shortUrl")){
            JSONObject obj = new JSONObject(content);
            JSONObject bean = (JSONObject) obj.get("bean");
            String shortUrl = "";
            if(bean!= null && bean.get("shortUrl") != null){
                shortUrl = (String) bean.get("shortUrl");
            }
            MvcResult queryResult = mockMvc.perform(
                    MockMvcRequestBuilders.get("/demo/queryLurlBySurl")
                            .param("surl", shortUrl)
                            .accept(MediaType.APPLICATION_JSON)).andReturn();
            content = queryResult.getResponse().getContentAsString();
        }
        //断言，判断返回的值是否正确
        Assert.assertTrue(content.contains("longUrl"));
    }

    @Test
    public void testQueryLurlBySurlNullParam() throws Exception {
        MvcResult saveResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/demo/queryLurlBySurl")
                        .param("surl", "")
                        .accept(MediaType.APPLICATION_JSON)).andReturn();
        //得到返回代码
        int status = saveResult.getResponse().getStatus();
        //得到返回结果
        String content = saveResult.getResponse().getContentAsString();
        //断言，判断返回的值是否正确
        Assert.assertTrue(content.contains("001"));
    }

    @Test
    public void testQueryLurlBySurlNullResult() throws Exception {
        ShortUrlCreateService service = new ShortUrlCreateService();
        service.to62(63);
        MvcResult saveResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/demo/queryLurlBySurl")
                        .param("surl", "I m not exists")
                        .accept(MediaType.APPLICATION_JSON)).andReturn();
        //得到返回代码
        int status = saveResult.getResponse().getStatus();
        //得到返回结果
        String content = saveResult.getResponse().getContentAsString();
        //断言，判断返回的值是否正确
        Assert.assertTrue(content.contains("003"));
    }


}
