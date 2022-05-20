package com.url.service.demo.service.impl;

import com.url.service.demo.service.UrlTransferService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class UrlTransferServiceImplTest {

	private final String URL_PRE = "https://scdt.cn/";

	private final String FULL_URL = "https://www.baidu.com";
	private final String FULL_URL2 = "http://www.baidu.com";

	private UrlTransferService uts = new UrlTransferServiceImpl();

	@Test
	void saveUrl() {
		Assert.assertEquals("invalid input", uts.saveUrl(null));
		Assert.assertEquals("invalid input", uts.saveUrl(""));
		Assert.assertEquals("invalid input", uts.saveUrl("12345678"));
		Assert.assertEquals(8 + URL_PRE.length(), uts.saveUrl(FULL_URL).length());
		Assert.assertEquals(8 + URL_PRE.length(), uts.saveUrl(FULL_URL2).length());
	}

	@Test
	void getFullUrl() {
		String shortUrl = uts.saveUrl(FULL_URL);
		Assert.assertEquals(FULL_URL, uts.getFullUrl(shortUrl.substring(URL_PRE.length())));

		Assert.assertEquals("not found", uts.getFullUrl("12345678"));
	}
}