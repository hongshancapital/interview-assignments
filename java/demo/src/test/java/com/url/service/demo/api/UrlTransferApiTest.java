package com.url.service.demo.api;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UrlTransferApiTest {

	private final String URL_PRE = "https://scdt.cn/";

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;


	@Test
	void saveUrl() throws Exception {
		int resLength = mockMvc.perform(MockMvcRequestBuilders
				.get("/saveUrl")
				.param("fullUrl", "https://www.baidu.com"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString().length();
		Assert.assertEquals(8 + URL_PRE.length(), resLength);
	}

	@Test
	void getFullUrl() throws Exception {
		String fullUrl = "https://www.baidu.com";
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/saveUrl").param("fullUrl", fullUrl);
		String shortUrl = mockMvc.perform(req).andReturn().getResponse().getContentAsString();
		MockHttpServletRequestBuilder req1 = MockMvcRequestBuilders.get("/getFullUrl").param("shortUrl", shortUrl.substring(URL_PRE.length()));
		Assert.assertEquals(mockMvc.perform(req1).andReturn().getResponse().getContentAsString(), fullUrl);
	}
}