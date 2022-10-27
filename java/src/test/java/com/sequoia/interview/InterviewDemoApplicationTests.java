package com.sequoia.interview;

import com.sequoia.interview.common.Result;
import com.sequoia.interview.controller.ShortLinkController;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class InterviewDemoApplicationTests {

	@Autowired
	private ShortLinkController shortLinkController;

	private String shortLinkStr;

	@Before
	public void storeShortLink(){
		Result<String> shortLink = shortLinkController.getShortLink("123");
		shortLinkStr = shortLink.getData();
		System.out.println(shortLinkStr);
	}

	/*@Test
	public void testGetShortLink(){
		Result<String> shortLink = shortLinkController.getShortLink("123");
		shortLinkStr = shortLink.getData();
		System.out.println(shortLink.getData());
	}*/

	@Test
	public void testGetLongLink(){
		Result<String> shortLink = shortLinkController.getShortLink("123");
		shortLinkStr = shortLink.getData();
		System.out.println(shortLinkStr);
		Result<String> longLink = shortLinkController.getLongLink(shortLinkStr);
		System.out.println(longLink.getData());
		Result<String> longLink2 = shortLinkController.getLongLink("124");
		Result<String> longLink3 = shortLinkController.getLongLink("");
		System.out.println(longLink2.getData());
	}

}
