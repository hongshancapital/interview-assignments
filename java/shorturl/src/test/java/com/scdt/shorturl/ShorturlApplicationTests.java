package com.scdt.shorturl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.scdt.shorturl.common.Result;
import com.scdt.shorturl.controller.ShortUrlController;
import com.scdt.shorturl.exception.ServerException;
import com.scdt.shorturl.utils.UrlCache;
import io.micrometer.core.instrument.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class ShorturlApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Test
	void contextLoads() {
	}

	@Autowired
	private ShortUrlController shortUrlController;

	@Test
	public void testLongUrlToShortUrl() {
		String longUrl = "https://jumps.sf-express.com/ui/#/";
		Result<Object> response = shortUrlController.longUrlToShortUrl(longUrl);
		Assert.isTrue(StringUtils.isNotEmpty((String)response.getData()), "success");
	}

	@Test
	public void testErrorLongUrlToShortUrl() {
		String longUrl = "11111123131";
		Result<Object> response = shortUrlController.longUrlToShortUrl(longUrl);
	}

	@Test
	public void testGetUrl() throws Exception {
		String longUrl = "https://jumps.sf-express.com/ui/#/";
		MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/longUrlToShortUrl").queryParam("url", longUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Result<String> result1 = JSONObject.parseObject(mvcResult1.getResponse().getContentAsString(), Result.class);
		String shortUrl = result1.getData();
		MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrlToLongUrl").queryParam("shortUrl", shortUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Result<String> result2 = JSONObject.parseObject(mvcResult2.getResponse().getContentAsString(), Result.class);
		assertEquals(longUrl, result2.getData());
	}

	/**
	 * 获取长域名
	 *
	 * @throws Exception
	 */
	@Test
	public void testExceptionGetUrl() throws Exception {
		String longUrl = "https://cn.bing.com/";
		MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/longUrlToShortUrl").queryParam("url", longUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Result<String> result1 = JSONObject.parseObject(mvcResult1.getResponse().getContentAsString(), Result.class);
		String shortUrl = result1.getData();
		MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrlToLongUrl").queryParam("shortUrl", "9999")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Result<String> result2 = JSONObject.parseObject(mvcResult2.getResponse().getContentAsString(), Result.class);
		//assertEquals(longUrl, result2.getData());
	}

	@Test
	public void testExceptionGetUrl2() throws Exception {
		ServerException serverException = new ServerException(500,"errr");
	}

	@Test
	public void testcommon() throws Exception {
		Result result = new  Result(200,"success");
		result.equals(result);
	}

	@Test
	public void testcommon2() throws Exception {
		UrlCache c = new UrlCache();
		UrlCache.contains("100");
	}
}
