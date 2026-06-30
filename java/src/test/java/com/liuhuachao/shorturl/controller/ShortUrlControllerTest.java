package com.liuhuachao.shorturl.controller;

import com.liuhuachao.shorturl.util.StoreUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * ShortUrlController Tester.
 * @author <liuhuachao>
 * @version 1.0
 * @since <pre>12/21/2021</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlControllerTest {
	private MockMvc mvc;

	/**
	 * 短域名
	 */
	private static final String SHORT_URL = "x.y/1W";

	/**
	 * 原始域名
	 */
	private static final String ORIGIN_URL = "http://www.liuhuachao.com/job";

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new ShortUrlController()).build();
	}

	/**
	 * Method: getOriginUrl(@RequestParam String shortUrl)
	 */
	@Test
	public void testGetOriginUrl() throws Exception {
		StoreUtil.store(SHORT_URL.replace("x.y/",""),ORIGIN_URL);

		mvc.perform(MockMvcRequestBuilders.get("/short-url/get")
				.param("shortUrl", "x.y/1W")
				.accept(MediaType.APPLICATION_JSON)
		)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	/**
	 * Method: getShortUrl(@RequestParam String originUrl)
	 */
	@Test
	public void testGetShortUrl() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/short-url/store")
				.param("originUrl", "http://www.liuhuachao.com/job")
				.accept(MediaType.APPLICATION_JSON)
		)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

} 
