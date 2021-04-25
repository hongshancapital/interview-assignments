package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.controller.ApiShortUrlController;
import com.example.entity.ShortUrlEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class RgApplicationTests {
	
	
	MockMvc mockMvc;
	
	@Autowired
    protected WebApplicationContext wac;	
    	
    @Autowired	
    ApiShortUrlController apiShortUrlController;	

	@Test
	void contextLoads() {
	}
	
	
	@Test
	void saveShortUrl() {
		
		
		ShortUrlEntity obj = new ShortUrlEntity();
		obj.setShortUrl("www.longooooooo.com");
		obj.setLongUrl("www.short.com");	
		
		
        
        
      //执行请求（使用POST请求，传递对象参数）
        MvcResult mvcResult;
		try {
			 //将参数转换成JSON对象
	        ObjectMapper mapper = new ObjectMapper();
	        String json = mapper.writeValueAsString(obj);
			mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/url/save").content(json).contentType(MediaType.APPLICATION_JSON)).andReturn();
			
			  //获取返回编码
	        int status = mvcResult.getResponse().getStatus();
	 
	        //获取返回结果
	        String content = mvcResult.getResponse().getContentAsString();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
      

	}
	

	@Test
	void queryShortUrl() {
		RequestBuilder request = null;
		//构造请求
		request = get("/api/url/www.sa.com"); 
		//执行请求
		try {
			mockMvc.perform(request) 
			        .andExpect(status().isOk())//返回HTTP状态为200
			        .andDo(print());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//打印结果

	}

}
