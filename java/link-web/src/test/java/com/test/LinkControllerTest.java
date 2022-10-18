package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson.JSON;
import com.link.application.Application;
import com.link.service.LinkService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class LinkControllerTest {
	private Logger log= LoggerFactory.getLogger(LinkControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    
    @InjectMocks
    @Autowired
    private LinkService linkService;
    
    @Test
	public void apiTest() throws Exception {
		// 构建请求
		MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders
				.get("/link/getShortUrl?longUrl=https://www.hszb.com/aaaaaaaaaaa/bbbbbbbbbbbbb/ccccccccccc/dddddddddd/eeeee/ffff/long/url")
				.contentType("text/html") // 指定请求的contentType头信息
				.accept(MediaType.APPLICATION_JSON); // 指定请求的Accept头信息
		ResultActions perform1 = mockMvc.perform(request1);
		perform1.andExpect(MockMvcResultMatchers.status().isOk());
		MvcResult mvcResult1 = perform1.andReturn();
		MockHttpServletResponse response1 = mvcResult1.getResponse();
		log.info("响应状态: {}", response1.getStatus());
		log.info("短地址{}", response1.getContentAsString());
		
		MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
				.get("/link/getLongUrl?shortUrl="+JSON.parseObject(response1.getContentAsString()).getString("data"))
				.contentType("text/html") // 指定请求的contentType头信息
				.accept(MediaType.APPLICATION_JSON); // 指定请求的Accept头信息
		ResultActions perform2 = mockMvc.perform(request2);
		perform2.andExpect(MockMvcResultMatchers.status().isOk());
		MvcResult mvcResult2 = perform2.andReturn();
		MockHttpServletResponse response2 = mvcResult2.getResponse();
		log.info("响应状态: {}", response2.getStatus());
		log.info("长地址{}", response2.getContentAsString());
	}
    
    @Test
 	public void apiFailTest() throws Exception {
 		// 构建请求
 		MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders
 				.get("/link/getShortUrl?longUrl=")
 				.contentType("text/html") // 指定请求的contentType头信息
 				.accept(MediaType.APPLICATION_JSON); // 指定请求的Accept头信息
 		ResultActions perform1 = mockMvc.perform(request1);
 		perform1.andExpect(MockMvcResultMatchers.status().isOk());
 		MvcResult mvcResult1 = perform1.andReturn();
 		MockHttpServletResponse response1 = mvcResult1.getResponse();
 		log.info("响应状态: {}", response1.getStatus());
 		log.info("失败短地址{}", response1.getContentAsString());
 		
 		MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
 				.get("/link/getLongUrl?shortUrl=")
 				.contentType("text/html") // 指定请求的contentType头信息
 				.accept(MediaType.APPLICATION_JSON); // 指定请求的Accept头信息
 		ResultActions perform2 = mockMvc.perform(request2);
 		perform2.andExpect(MockMvcResultMatchers.status().isOk());
 		MvcResult mvcResult2 = perform2.andReturn();
 		MockHttpServletResponse response2 = mvcResult2.getResponse();
 		log.info("响应状态: {}", response2.getStatus());
 		log.info("失败长地址{}", response2.getContentAsString());
 	}

}
