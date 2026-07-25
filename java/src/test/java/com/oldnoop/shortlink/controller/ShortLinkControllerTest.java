package com.oldnoop.shortlink.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.oldnoop.shortlink.model.ApiResult;
import com.oldnoop.shortlink.service.ShortLinkService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShortLinkControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ShortLinkService mockShortLinkService;
    
    private static final String API_CREATE_URI = "/shortlink/create";
    private static final String API_SEARCH_URI = "/shortlink/search";
    
    @Test
    @SuppressWarnings("rawtypes")
    public void create() throws Exception {
    	
    	String longLink = "https://github.com/scdt-china/interview-assignments";
    	String shortLink = "https://5.cn/c";
		ArrayList<String> longLinkList = Lists.newArrayList(longLink);
		ArrayList<String> shortLinkList = Lists.newArrayList(shortLink);
    	
    	Mockito.when(mockShortLinkService.create(longLinkList)).thenReturn(shortLinkList);
    	
        String respContent = mockMvc.perform(
        		post(API_CREATE_URI)
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(JSON.toJSONString(longLinkList)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
		ApiResult apiResult = JSON.parseObject(respContent, ApiResult.class);
        Assert.assertEquals(shortLinkList, apiResult.getData());
    }
    
    
    @Test
    @SuppressWarnings("rawtypes")
    public void createEx() throws Exception {
    	
    	String longLink = "github.com/scdt-china/interview-assignments";
    	ArrayList<String> longLinkList = Lists.newArrayList(longLink);
    	{
    		Mockito.when(mockShortLinkService.create(longLinkList)).thenThrow(new IllegalStateException("链接不合法"));
    		String respContent = mockMvc.perform(
    				post(API_CREATE_URI)
    				.contentType(MediaType.APPLICATION_JSON_VALUE)
    				.content(JSON.toJSONString(longLinkList)))
    				.andDo(print())
    				.andExpect(status().isOk())
    				.andReturn().getResponse().getContentAsString();
    		ApiResult apiResult = JSON.parseObject(respContent, ApiResult.class);
    		Assert.assertFalse(apiResult.isSuccess());
    	}
    	
    }
    
    @Test
    @SuppressWarnings("rawtypes")
    public void createEx2() throws Exception {
    	
    	String longLink = "github.com/scdt-china/interview-assignments";
    	ArrayList<String> longLinkList = Lists.newArrayList(longLink);
    	{
    		Mockito.when(mockShortLinkService.create(longLinkList)).thenThrow(new IllegalStateException());
    		String respContent = mockMvc.perform(
    				post(API_CREATE_URI)
    				.contentType(MediaType.APPLICATION_JSON_VALUE)
    				.content(JSON.toJSONString(longLinkList)))
    				.andDo(print())
    				.andExpect(status().isOk())
    				.andReturn().getResponse().getContentAsString();
    		ApiResult apiResult = JSON.parseObject(respContent, ApiResult.class);
    		Assert.assertFalse(apiResult.isSuccess());
    	}
    	
    }

	@Test
	@SuppressWarnings("rawtypes")
    public void search() throws Exception {
    	
    	String longLink = "http://github.com/scdt-china/interview-assignments";
    	String shortLink = "http://5.cn/c";
    	
    	Mockito.when(mockShortLinkService.search(shortLink)).thenReturn(longLink);
    	
        String respContent = mockMvc.perform(
        		post(API_SEARCH_URI)
        		.param("shortLink", shortLink))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ApiResult apiResult = JSON.parseObject(respContent, ApiResult.class);
        Assert.assertEquals(longLink, apiResult.getData());
    }
    
}