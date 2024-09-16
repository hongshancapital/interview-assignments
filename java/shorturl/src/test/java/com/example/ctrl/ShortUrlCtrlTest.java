package com.example.ctrl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @projectName: shortUrl
 * @package: com.example.ctrl
 * @className: ShortUrlCtrlTest
 * @description: 短域名ctrl接口单元测试类
 * @author: Kai
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlCtrlTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp (){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void transLong() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/url/transLong")
                .param("longUrl","https://www.cnblogs.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("A3u6aaaa"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void transShort() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/url/transShort")
                .param("shortUrl","abcdefgh"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))       //因Map存储使用mockMvc会产生新的ctrl故肯定为0
                .andDo(MockMvcResultHandlers.print());
    }

}