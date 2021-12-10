package com.scdt.interview.leon.test;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.scdt.interview.leon.bean.RecoverUrlRO;
import com.scdt.interview.leon.bean.ShortenUrlRO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 集成测试类
 *
 * @author leon
 * @since 2021/10/26
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class IntegrationTest {

	private static String tempShortUrl = "";

	private final String oriUrl = "https://sports.sina.com.cn/china/";

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
	}

	@Test
	@Order(1)
	public void shortenTest() throws Exception {
		ShortenUrlRO ro = new ShortenUrlRO();
		ro.setOriUrl(oriUrl);

		MvcResult result = mockMvc.perform(post("/url/shorten")
						.contentType(MediaType.APPLICATION_JSON) //请求媒体类型
						.content(JSONUtil.toJsonStr(ro))  //发送请求数据
				)
				.andExpect(status().isOk())// 预期返回httpstatus状态
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		JSONObject response = JSONUtil.parseObj( result.getResponse().getContentAsString() );
		tempShortUrl = (String)response.get("result");

		assertNotNull(tempShortUrl);
		assertEquals(8, tempShortUrl.length());

		log.info("shortUrl = "+ tempShortUrl);
	}

	@Test
	@Order(2)
	public void recoverTest() throws Exception {
		RecoverUrlRO ro = new RecoverUrlRO();
		ro.setShortUrl(tempShortUrl);

		MvcResult result = mockMvc.perform(post("/url/recover")
						.contentType(MediaType.APPLICATION_JSON) //请求媒体类型
						.content(JSONUtil.toJsonStr(ro))  //发送请求数据
				)
				.andExpect(status().isOk())// 预期返回httpstatus状态
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))// 预期返回值的媒体类型
				.andReturn();// 返回执行请求的结果

		JSONObject response = JSONUtil.parseObj( result.getResponse().getContentAsString() );
		String recoverUrl = (String)response.get("result");

		assertNotNull(recoverUrl);
		assertEquals(oriUrl, recoverUrl);

		log.info("oriUrl = "+ recoverUrl);
	}

	@Test
	@Order(3)
	public void paraExceptionTest() throws Exception {
		ShortenUrlRO ro = new ShortenUrlRO();
		ro.setOriUrl("");

		MvcResult result = mockMvc.perform(post("/url/shorten")
						.contentType(MediaType.APPLICATION_JSON) //请求媒体类型
						.content(JSONUtil.toJsonStr(ro))  //发送请求数据
				)
				.andExpect(status().isOk())// 预期返回httpstatus状态
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))// 预期返回值的媒体类型
				.andReturn();// 返回执行请求的结果

		JSONObject response = JSONUtil.parseObj( result.getResponse().getContentAsString() );
		String rtnCode = (String)response.get("rtnCode");
		String rtnMsg = (String)response.get("rtnMsg");

		assertNotNull(rtnCode);
		assertEquals("400", rtnCode);

		log.info("errorMsg = "+ rtnMsg);
	}

	@Test
	@Order(4)
	public void bizExceptionTest() throws Exception {
		RecoverUrlRO ro = new RecoverUrlRO();
		ro.setShortUrl("123456789");

		MvcResult result = mockMvc.perform(post("/url/recover")
						.contentType(MediaType.APPLICATION_JSON) //请求媒体类型
						.content(JSONUtil.toJsonStr(ro))  //发送请求数据
				)
				.andExpect(status().isOk())// 预期返回httpstatus状态
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))// 预期返回值的媒体类型
				.andReturn();// 返回执行请求的结果

		JSONObject response = JSONUtil.parseObj( result.getResponse().getContentAsString() );
		String rtnCode = (String)response.get("rtnCode");
		String rtnMsg = (String)response.get("rtnMsg");

		assertNotNull(rtnCode);
		assertEquals("500", rtnCode);

		log.info("errorMsg = "+ rtnMsg);
	}


}
