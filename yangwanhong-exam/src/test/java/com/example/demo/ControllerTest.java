package com.example.demo;

import com.example.demo.controller.UrlConvertController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ControllerTest {

	@Autowired
	private UrlConvertController urlConvertController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(urlConvertController).build();
	}

	@Test
	public void getShortUrlTest() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/urlConvert/getShortUrl?url=https://www.baidu.com/fasf"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		Assert.assertTrue("短域名超出长度限制",mvcResult.getResponse().getContentAsString().length()<=8);

	}

	@Test
	public void convertToLongUrl() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/urlConvert/convertToLongUrl?url=https://www.baidu.com/fasf")
				 .requestAttr("url","https://www.baidu.com/fasf"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void convertAndRedirect() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/urlConvert/getShortUrl?url=https://www.baidu.com/fasf"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		String shortUrl = mvcResult.getResponse().getContentAsString();

		mockMvc.perform(MockMvcRequestBuilders.post("/urlConvert/convertToLongUrl?url="+shortUrl))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
}
