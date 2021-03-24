package com.wjup.shorturl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjup.shorturl.controller.UrlController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShorturlApplicationTests extends BaseShortUrlTest {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UrlController urlController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
    }

    /**
     * 测试长变短成功案例
     * @throws Exception
     */

    @Test
    public void testSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/url/longtoshort")
                .accept(MediaType.APPLICATION_JSON_UTF8).param("url", "https://github.com/scdt-china/interview-assignments/tree/master/java"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


        log.info(mvcResult.getResponse().getContentAsString());
    }

    /**
     * 测试长变短失败案例
     * @throws Exception
     */
    @Test
    public void testFailed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/url/longtoshort")
                .accept(MediaType.APPLICATION_JSON_UTF8).param("url", "I Love LRD"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        String fileJson = mvcResult.getResponse().getContentAsString();
        Result r = om.readValue(fileJson, Result.class);

        //Assert.assertEquals(r.getMessage(),"非法地址");
    }

    /**
     * 测试转向
     * @throws Exception
     */
    @Test
    public void testRedirect() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/url/yeE36z"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
