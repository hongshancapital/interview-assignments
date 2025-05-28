package com.example.demo;

import com.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UrlService urlService;

	@Test
	void contextLoads() {
		String shortUrl = addUrl();
		getUrl(shortUrl);
	}

	void getUrl(String shortUrl) {
		urlService.getUrl(shortUrl);
	}

	String addUrl() {
		return urlService.addUrl("https://www.baidu.com/");
	}

}
