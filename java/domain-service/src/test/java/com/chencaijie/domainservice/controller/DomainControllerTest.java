package com.chencaijie.domainservice.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DomainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveDomainName() throws Exception{
        //请求的url,请求的方法是get
        String responseString = mockMvc.perform(MockMvcRequestBuilders.post("/domainService/saveDomainName?domainName=www.sequoiacap.com")
                .contentType(MediaType.TEXT_PLAIN_VALUE) //数据的格式
                .accept(MediaType.ALL_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk())  //返回的状态是200
                .andDo(MockMvcResultHandlers.print()) //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString(); //将相应的数据转换为字符

        System.out.println(responseString);
    }

    @Test
    void getLongDomainName() throws Exception {
        //请求的url,请求的方法是get
        String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/domainService/getDomainName?shortDomain=hi")
                .contentType(MediaType.TEXT_PLAIN_VALUE) //数据的格式
                .accept(MediaType.ALL_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk())  //返回的状态是200
                .andDo(MockMvcResultHandlers.print()) //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString(); //将相应的数据转换为字符

        System.out.println(responseString);
    }


}