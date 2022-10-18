package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.link.application.Application;
import com.link.service.LinkService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class LinkServiceTest {
	@Autowired
	private LinkService linkService;
	@Test
	public void linkTest() throws Exception {
		String shortUrl = linkService.getShortUrl("https://www.hszb.com/aaaaaaaaaaa/bbbbbbbbbbbbb/ccccccccccc/dddddddddd/eeeee/ffff/long/url");
		System.out.println("短地址：" + shortUrl);
		System.out.println("长地址:" + linkService.getLongUrl(shortUrl));
	}
}
