package com.liu.shorturl.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Description： TODO
 * Author: liujiao
 * Date: Created in 2021/11/11 17:32
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 根据短域名获取对应的长域名
     * @throws Exception
     */
    @Test
    public void getLongUrlByShorUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/shorturl/getLongUrlByShorUrl?surl=1KlFt3c3rmU"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void addShortUrlByLongUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/addShortUrlByLongUrl?url=http://www.sds.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}