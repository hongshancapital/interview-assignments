package pers.dongxiang.shorturl.controller;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pers.dongxiang.shorturl.dto.OriginUrlDTO;
import pers.dongxiang.shorturl.util.R;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createShortUrl() {

        try{
            // 正常请求
            this.mockMvc.perform(MockMvcRequestBuilders.post("/url/create")
            .param("originUrl","http://www.baidu.com"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            // URL错误
            this.mockMvc.perform(MockMvcRequestBuilders.post("/url/create")
                    .param("originUrl","http:/www.baidu.com"))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                    .andReturn();

            // 缺少参数
            this.mockMvc.perform(MockMvcRequestBuilders.post("/url/create"))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                    .andReturn();

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    @Test
    void getOriginUrl() {
        try{
            // 正常请求
            this.mockMvc.perform(MockMvcRequestBuilders.get("/url/search")
            .param("shortUrl","http://dx.cn/qGGGGGGK"))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            // 非法参数
            this.mockMvc.perform(MockMvcRequestBuilders.get("/url/search")
                    .param("shortUrl","http://dx.cn/qGGGGGG"))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError());

            // 缺少参数
            this.mockMvc.perform(MockMvcRequestBuilders.get("/url/search"))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError());

        }catch(Exception e){

        }
    }
}
