package com.gc.shorturl;

import com.gc.shorturl.common.BaseResponse;
import com.gc.shorturl.controller.ShortUrlController;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ShortUrlController controller;

    @Test
    void getShortUrl() {
        BaseResponse response = controller.convert2ShortUrl("http://www.baidu.com");
        System.out.println(response.getData());
    }

    @Test
    void getOriginalUrl() {
        BaseResponse<String> response = controller.convert2ShortUrl("http://www.baidu.com");
        if (response.getCode() == 200) {
            response = controller.convert2OriginalUrl(response.getData());
            System.out.println(response.getData());
        }
    }

    @Test
    void convert2ShortUrl() {
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/url/convert2ShortUrl")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("")
                    .param("url", "http://www.baidu.com"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
