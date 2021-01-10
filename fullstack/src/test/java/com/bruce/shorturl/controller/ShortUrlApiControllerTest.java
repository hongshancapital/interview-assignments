package com.bruce.shorturl.controller;

import com.bruce.shorturl.data.TestData;
import com.bruce.shorturl.exception.ErrorCode;
import com.bruce.shorturl.exception.GenericRuntimeException;
import com.bruce.shorturl.model.ResultStruct;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortUrlApiControllerTest implements InitializingBean {

	@Autowired
	private ShortUrlApiController shortUrlApiController;
	@Autowired
	private MockMvc mockMvc;

	@Override public void afterPropertiesSet() throws Exception {
		Assertions.assertNotNull(mockMvc);
	}

	@BeforeEach
	public void setup() {

	}

	/**
	 * 生成短链接
	 */
	@Test public void generateShortUrl1() {
		ResultStruct result = shortUrlApiController.generateShortUrl(TestData.DEFAULT_HASH_VALUE);
		Assertions.assertTrue(ResultStruct.isSuccess(result));
	}

	/**
	 * 生成短链接
	 */
	@Test public void generateShortUrl2() {
		ResultStruct result = shortUrlApiController.generateShortUrl(TestData.DEFAULT_HASH_VALUE_2);
		Assertions.assertTrue(ResultStruct.isSuccess(result));
	}

	/**
	 * 生成短链接，传入null
	 */
	@Test
	public void generateShortUrl3() {
		GenericRuntimeException exception = Assertions.assertThrows(
				GenericRuntimeException.class, () -> shortUrlApiController.generateShortUrl(null));
		Assertions.assertEquals(ErrorCode.ERROR_CODE_SHORTURL_PARAM,  exception.getErrCode());
	}


	/**
	 * 生成短链接，传入空串
	 */
	@Test
	public void generateShortUrl4() {
		GenericRuntimeException exception = Assertions.assertThrows(
				GenericRuntimeException.class, () -> shortUrlApiController.generateShortUrl(""));
		Assertions.assertEquals(ErrorCode.ERROR_CODE_SHORTURL_PARAM,  exception.getErrCode());
	}

	/**
	 * 生成短链接，传入空串
	 */
	@Test
	public void generateShortUrl5() {
		GenericRuntimeException exception = Assertions.assertThrows(
				GenericRuntimeException.class, () -> shortUrlApiController.generateShortUrl(" "));
		Assertions.assertEquals(ErrorCode.ERROR_CODE_SHORTURL_PARAM,  exception.getErrCode());
	}

	/**
	 * 生成短链接，传入非http前缀的串
	 */
	@Test
	public void generateShortUrl6() {
		GenericRuntimeException exception = Assertions.assertThrows(
				GenericRuntimeException.class, () -> shortUrlApiController.generateShortUrl("scheme://"));
		Assertions.assertEquals(ErrorCode.ERROR_CODE_SHORTURL_PARAM,  exception.getErrCode());
	}




	/**
	 * 生成短链接，传入非http前缀的串
	 */
	@Test
	public void generateShortUrl_mock_1() throws Exception {
		String url = "http://localhost:8080/api/generateShortUrl";
		MvcResult mockResult = mockMvc.perform(MockMvcRequestBuilders.get(url).param("fullUrl", TestData.DEFAULT_HASH_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		//获取数据
		JSONObject jsonObject =new JSONObject(mockResult.getResponse().getContentAsString());


//		boolean exists = jsonObject.has("errCode");
//		Integer errCode = (Integer)jsonObject.getJSONObject("errCode");
//		Assertions.assertNull(errCode);

		String data = (String)jsonObject.get("data");
		Assertions.assertNotNull(data);

	}


}