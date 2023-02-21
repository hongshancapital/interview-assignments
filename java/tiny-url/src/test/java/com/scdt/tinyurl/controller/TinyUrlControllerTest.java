package com.scdt.tinyurl.controller;

import com.scdt.tinyurl.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class TinyUrlControllerTest {


    @Autowired
    private AppConfig appConfig;


    @Autowired
    private MockMvc mock;

    /*
      单元测试前先切换到dev环境，配置spring.profiles.active=dev
      设置application.storage.max-capacity为3，超时时间设置为5s，expired-after-write.duration: 5
      设置application.cache.max-percentage为0.8，超时时间为5s
     */


    @Test
    void fetchTinyUrl() throws Exception {


        String request = "{\n" +
                "  \"longUrl\": \"http://www.baidu.com1\"\n" +
                "}";

        postRequest(request,"200");

        //测试重复请求从Cache中获取

        postRequest(request,"200");

        //等待数据超时过期
        Thread.sleep(5000);

        fetchTinyExceed();
    }

    private void postRequest(String request,String errorCode) throws Exception {
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchTinyUrl")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .content(request)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(errorCode)) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info(content);
    }


    void fetchTinyExceed() throws Exception {

        String request2 = "{\n" +
                "  \"longUrl\": \"http://www.baidu.com2\"\n" +
                "}";

        postRequest(request2,"200");

        String request3 = "{\n" +
                "  \"longUrl\": \"http://www.baidu.com3\"\n" +
                "}";

        postRequest(request3,"200");

        String request4 = "{\n" +
                "  \"longUrl\": \"http://www.baidu.com4\"\n" +
                "}";

        postRequest(request4,"200");


        String request5 = "{\n" +
                "  \"longUrl\": \"http://www.baidu.com5\"\n" +
                "}";

        postRequest(request5,"501");


    }

    @Test
    void fetchLongUrl() throws Exception {


        String request = "{\n" +
                "  \"longUrl\": \"http://www.google.com\"\n" +
                "}";

        postRequest(request,"200");


        getRequest("00000000","200");

        getRequest("00000005","404");

        //测试broker-id为非本机生成的场景
        appConfig.setBrokerId("20");

        getRequest("00000000","405");



    }

    private void getRequest(String param,String errorCode) throws Exception {
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.get("/fetchLongUrl")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("tinyUrl",param)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(errorCode)) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info(content);
    }
}