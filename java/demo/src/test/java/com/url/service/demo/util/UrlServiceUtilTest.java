package com.url.service.demo.util;

import com.url.service.demo.constant.UrlServiceConstant;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UrlServiceUtilTest {

	private static final Logger logger = LoggerFactory.getLogger(UrlServiceUtilTest.class);
	
	private final String FULL_URL = "https://www.baidu.com";

	private final String URL_PRE = "https://scdt.cn/";

	@InjectMocks
	UrlServiceUtil us;

	@Test
	void saveUrl() {
		String shortUrl = UrlServiceUtil.saveUrl(FULL_URL);
		Assert.assertEquals(UrlServiceConstant.URL_PRE.length() + 8, shortUrl.length());


		try (MockedStatic<AlgorithmUtil> us = Mockito.mockStatic(AlgorithmUtil.class) ){
			us.when(() -> {
				AlgorithmUtil.md5Url(FULL_URL);
			}).thenReturn(new String[] {});
			us.when(() -> {
				AlgorithmUtil.randomUrl();
			}).thenReturn("12345678");
			Assert.assertEquals(UrlServiceConstant.URL_PRE.length() + 8, UrlServiceUtil.saveUrl(FULL_URL).length());
		} catch (Exception e) {
			logger.error("error: ", e);
		}
	}

	@Test
	void getMd5Url() {
		Assert.assertEquals(8 + URL_PRE.length(), UrlServiceUtil.getMd5Url(FULL_URL).length());

		try (MockedStatic<AlgorithmUtil> us = Mockito.mockStatic(AlgorithmUtil.class) ){
			us.when(() -> {
				AlgorithmUtil.md5Url(FULL_URL);
			}).thenReturn(new String[] {});
			Assert.assertEquals(null, UrlServiceUtil.saveUrl(FULL_URL).length());
		} catch (Exception e) {
			logger.error("error: ", e);
		}
	}

	@Test
	void getRandomUrl() {
		Assert.assertEquals(8 + URL_PRE.length(), UrlServiceUtil.getRandomUrl(FULL_URL).length());
	}
}