package com.wjup.shorturl.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: TODO
 * @classname: UrlControllerTest
 * @author niuxing@huaxiapawn
 * @date 2021/3/22 16:53
*/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@AutoConfigureMockMvc
public class UrlControllerTest {


    /**
     * 利用TestRestTemplate进行测试。
     */
    @Autowired
    private TestRestTemplate template;

    /**
     * 测试长链接转换接口
     */
    /*@Test
    public void testCreateUrl(){
        HttpServletRequest forObject = template.postForObject("create","https://interview.nowcoder.com/interview/20826497/interviewee?code=KUMQo4t9#userCode", String.class);
        Assert.assertNotNull(forObject);
    }*/

    @Before
    public void init() {
        log.info("测试开始。。。");
    }

    @After
    public void after(){
        log.info("测试结束。。。");
    }

    /**
     * 空的测试方法  无用
     */
    @Test
    public void contextLoads() {
    }

    /**
     * 利用MockMvc进行测试。
     */
    @Resource
    private MockMvc mockMvc;

    /**
     * 长链接转为短链接
     * @throws Exception
     */
    @Test
    public void testUrlCreate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/create")
                .accept(MediaType.APPLICATION_JSON_UTF8).param("longUrl", "https://github.com/scdt-china/interview-assignments/tree/master/java"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info("..."+mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testUrlrevert() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/{shortUrlId}")
                .accept(MediaType.APPLICATION_JSON_UTF8).param("shortUrlId", "u2oi3n"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info("..."+mvcResult.getResponse().getContentAsString());
    }

    /*
    *
    * 其余接口是我自己参照百度进行维护的  在此处不做test
    *
    * */

}
