package com.asan.shorturlserver;

import com.asan.shorturlserver.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShortUrlServerApplicationTests {


	@Autowired
	ShortUrlService shortUrlService ;

	@Test
	public void toShort() throws Exception {
		String result =shortUrlService.toShort("https://www.google.com");
		System.out.println(result);
	}

	@Test
	public void getOriginUrl() throws Exception {
		String code =shortUrlService.toShort("https://www.google.com");
		String result =shortUrlService.getOriginUrl(code);
		System.out.println(result);
	}


	@Test
	void contextLoads() {
	}
}
