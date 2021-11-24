package com.example.homework.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import com.example.homework.HomeworkApplicationTests;

public class ShortUrlControllerTest extends HomeworkApplicationTests{
	@Test
    public void testGetShortUrl() throws Exception{
		try {
        MvcResult result = mockMvc.perform(post("/shortUrl/getShortUrl").param("longUrl", "http://www.baidu.com"))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();

        Object handler = result.getHandler();
        System.out.println(handler);
		}catch(Throwable e) {
			e.printStackTrace();
		}
    }
	
	@Test
	public void testGetLongUrl() throws Exception {
		MvcResult result = mockMvc.perform(post("/shortUrl/getLongUrl").param("shortUrl", "http://a.cn/Jj2aBD"))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();

        Object handler = result.getHandler();
        System.out.println(handler);
	}
}
