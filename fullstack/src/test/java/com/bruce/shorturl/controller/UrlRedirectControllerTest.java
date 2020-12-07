package com.bruce.shorturl.controller;

import com.bruce.shorturl.data.TestData;
import com.bruce.shorturl.service.IShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UrlRedirectControllerTest implements InitializingBean {

	@Autowired
	private UrlRedirectController urlRedirectController;
	@Autowired
	private IShortUrlService shortUrlService;
	@Autowired
	private MockMvc mockMvc;

	@Override public void afterPropertiesSet() throws Exception {
		Assertions.assertNotNull(mockMvc);
	}

	@BeforeEach
	public void setup() {
	}

	@Test void hashRedirect0() {
		String result = urlRedirectController.hashRedirect(null);
		Assertions.assertEquals(result, "error/shorturl");

		result = urlRedirectController.hashRedirect(" ");
		Assertions.assertEquals(result, "error/shorturl");
	}

	@Test void hashRedirect1() {
		String result = urlRedirectController.hashRedirect("1");
		Assertions.assertEquals(result, "error/shorturl");
	}

	@Test void hashRedirect2() {

		String shortUrl = shortUrlService.generateShortUrl(TestData.DEFAULT_HASH_VALUE);
		Assertions.assertNotEquals(shortUrl, "error/shorturl");

		String hash = StringUtils.substring(shortUrl, shortUrl.lastIndexOf("/")+1);
		String result = urlRedirectController.hashRedirect(hash);
		Assertions.assertNotNull(result);
	}


}