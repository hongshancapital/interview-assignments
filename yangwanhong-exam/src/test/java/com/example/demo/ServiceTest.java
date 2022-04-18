package com.example.demo;

import com.example.demo.controller.UrlConvertController;
import com.example.demo.service.UrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

	@Autowired
	private UrlService urlService;

	@Test
	public void getShortUrlTest() {
		String longUrl = "https://www.baidu.com/hhh";
		String shortUrl = urlService.getShortUrl(longUrl);
		System.out.println(shortUrl);
		Assert.assertTrue("短域名超出长度限制",shortUrl.length()<=8);

		longUrl = urlService.convertToLongUrl(shortUrl);
		System.out.println(longUrl);
		Assert.assertTrue("解析错误", !StringUtils.isEmpty(longUrl));
	}

	@Test
	public void convertToLongUrl() {
		String shortUrl = "uhfs1234";
		String longUrl = urlService.convertToLongUrl(shortUrl);
		System.out.println(longUrl);
		Assert.assertTrue("解析错误", !StringUtils.isEmpty(longUrl));
	}
}
