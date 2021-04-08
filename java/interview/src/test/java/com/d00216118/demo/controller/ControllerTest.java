package com.d00216118.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d00216118.demo.dto.TinyUrlRequestDTO;
import com.d00216118.demo.dto.UrlRequestDTO;
import com.d00216118.demo.dto.UserRequestDTO;
import com.d00216118.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.DigestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 10:03 下午 2021/4/4
 **/

@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(UserController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;


//    @Test
    @SneakyThrows
    public void authorizeTest() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("test1");
        userRequestDTO.setPassword("test1");
        userRequestDTO.setTimestamp("1617191330683");
        userRequestDTO.setSign("9d2271213eab7fd706f7f7077f01e7a0");

        ResultActions resultActions = mockMvc.perform(post("/user/authorize")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.toJSONString(userRequestDTO))
        ).andExpect(status().isOk()).andDo(print());
    }


//    @Test
    @SneakyThrows
    public void createurlTest(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("test1");
        userRequestDTO.setPassword("test1");
        userRequestDTO.setTimestamp("1617191330683");
        userRequestDTO.setSign("9d2271213eab7fd706f7f7077f01e7a0");

        String resultUser = mockMvc.perform(post("/user/authorize")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.toJSONString(userRequestDTO))
        ).andReturn().getResponse().getContentAsString();

        String token=JSONObject.parseObject(resultUser).getJSONObject("value").get("token")+"";
        String timeStamp=System.currentTimeMillis()+"";
        String url="https://github.com/jade75/interview-assignments";
        String temp= url+"|"+token+"|"+"test1"+"|"+timeStamp;
        String sign= DigestUtils.md5DigestAsHex(temp.getBytes());

        System.out.println("======"+token);

        UrlRequestDTO urlRequestDTO=new UrlRequestDTO();
        urlRequestDTO.setTimestamp(timeStamp);
        urlRequestDTO.setUsername("test1");
        urlRequestDTO.setUrl(url);
        urlRequestDTO.setSign(sign);
        urlRequestDTO.setToken(token);

        ResultActions resultActions = mockMvc.perform(post("/url/createurl")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.toJSONString(urlRequestDTO))
        ).andExpect(status().isOk()).andDo(print());
    }



    @Test
    @SneakyThrows
    public void retrieveurlTest(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("test1");
        userRequestDTO.setPassword("test1");
        userRequestDTO.setTimestamp("1617191330683");
        userRequestDTO.setSign("9d2271213eab7fd706f7f7077f01e7a0");

        String resultUser = mockMvc.perform(post("/user/authorize")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.toJSONString(userRequestDTO))
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        String token=JSONObject.parseObject(resultUser).getJSONObject("value").get("token")+"";
        String timeStamp=System.currentTimeMillis()+"";
        String url="https://github.com/jade75/interview-assignments";
        String temp= url+"|"+token+"|"+"test1"+"|"+timeStamp;
        String sign= DigestUtils.md5DigestAsHex(temp.getBytes());

        System.out.println("======"+token);

        UrlRequestDTO urlRequestDTO=new UrlRequestDTO();
        urlRequestDTO.setTimestamp(timeStamp);
        urlRequestDTO.setUsername("test1");
        urlRequestDTO.setUrl(url);
        urlRequestDTO.setSign(sign);
        urlRequestDTO.setToken(token);

        String resultTinyurl = mockMvc.perform(post("/url/createurl")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.toJSONString(urlRequestDTO))
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        String tinyUrl=JSONObject.parseObject(resultTinyurl).getJSONObject("value").get("tinyUrl")+"";
        String temp2= tinyUrl+"|"+token+"|"+"test1"+"|"+timeStamp;
        String md5Sign2=DigestUtils.md5DigestAsHex(temp2.getBytes());

        TinyUrlRequestDTO tinyUrlRequestDTO=new TinyUrlRequestDTO();
        tinyUrlRequestDTO.setTimestamp(timeStamp);
        tinyUrlRequestDTO.setTinyUrl(tinyUrl);
        tinyUrlRequestDTO.setUsername("test1");
        tinyUrlRequestDTO.setToken(token);
        tinyUrlRequestDTO.setSign(md5Sign2);

        mockMvc.perform(post("/url/retrieveurl")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.toJSONString(tinyUrlRequestDTO))
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();



    }


}
