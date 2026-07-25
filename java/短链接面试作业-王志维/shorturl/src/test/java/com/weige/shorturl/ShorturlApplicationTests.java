package com.weige.shorturl;

import com.weige.shorturl.Service.DomainService;
import com.weige.shorturl.common.ShortUrlUtils;
import com.weige.shorturl.common.Storage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@SpringBootTest
class ShorturlApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testStorage() {
		Storage.set("testKey","testData");
		Assert.isTrue("testData".equals(Storage.find("testKey")));
	}

	@Autowired
	DomainService domainService;

	@Test
	void testGenShortUrl(){
		String longUrl = "https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests";
		String shortUrl = ShortUrlUtils.shortUrl(longUrl);
		Assert.notNull(shortUrl);
	}

	@Test
	void testWrite(){
		String longUrl = "https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests";
		String shortUrl = domainService.write(longUrl);
		Assert.notNull(shortUrl);
	}


	@Test
	void testRead(){
		String longUrl = "https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests";
		String shortUrl = domainService.write(longUrl);
		Assert.notNull(domainService.read(shortUrl));
	}


}
