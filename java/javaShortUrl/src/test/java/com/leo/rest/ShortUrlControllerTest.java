package com.leo.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.leo.ShortUrlApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ShortUrlApplication.class })

public class ShortUrlControllerTest {
	@Autowired
	private ShortUrlController shortUrlController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(shortUrlController).build();
	}

	@Test
	public void testShortUrlToNormal() throws Exception {
		{
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.get("/short2normal").accept(MediaType.APPLICATION_JSON)
							.param("shortUrl", ""))
					.andExpect(MockMvcResultMatchers.status().is(406)).andDo(MockMvcResultHandlers.print()).andReturn();

		}
		{
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.get("/short2normal").accept(MediaType.APPLICATION_JSON)
							.param("shortUrl", "1234567"))
					.andExpect(MockMvcResultMatchers.status().is(404)).andDo(MockMvcResultHandlers.print()).andReturn();
		}
		
		{
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.get("/short2normal").accept(MediaType.APPLICATION_JSON)
							.param("shortUrl", "123456"))
					.andExpect(MockMvcResultMatchers.status().is(406)).andDo(MockMvcResultHandlers.print()).andReturn();
		}
		
		
		{
			
			MvcResult mvcResult1 = mockMvc
					.perform(MockMvcRequestBuilders.get("/normal2short").accept(MediaType.APPLICATION_JSON).param("url",
							"http://www.baidu.com"))
					.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
			String res1 = mvcResult1.getResponse().getContentAsString();
			Assert.assertEquals(7, res1.length());
			
			MvcResult mvcResult2 = mockMvc
					.perform(MockMvcRequestBuilders.get("/short2normal").accept(MediaType.APPLICATION_JSON)
							.param("shortUrl", res1))
					.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
			String res2 = mvcResult2.getResponse().getContentAsString();
			Assert.assertEquals("http://www.baidu.com", res2);
		}
	}

	@Test
	public void testNormalUrlToShort() throws Exception {
		{
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.get("/normal2short").accept(MediaType.APPLICATION_JSON)
							.param("url", ""))
					.andExpect(MockMvcResultMatchers.status().is(406)).andDo(MockMvcResultHandlers.print()).andReturn();

		}
		{
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.get("/normal2short").accept(MediaType.APPLICATION_JSON)
							.param("url", "fff://www.baidu.com"))
					.andExpect(MockMvcResultMatchers.status().is(406)).andDo(MockMvcResultHandlers.print()).andReturn();
		}
		
		{
			StringBuffer sb = new StringBuffer();
			for (int i =0; i < 512; i++) {
				sb.append("a");
			}
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.get("/normal2short").accept(MediaType.APPLICATION_JSON)
							.param("url", "http://www.baidu.com/" + sb.toString()))
					.andExpect(MockMvcResultMatchers.status().is(406)).andDo(MockMvcResultHandlers.print()).andReturn();
		}
		
		{
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.get("/normal2short").accept(MediaType.APPLICATION_JSON)
							.param("url", "http://abc"))
					.andExpect(MockMvcResultMatchers.status().is(406)).andDo(MockMvcResultHandlers.print()).andReturn();
		}
	}

}

