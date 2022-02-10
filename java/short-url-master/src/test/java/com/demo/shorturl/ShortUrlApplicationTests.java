package com.demo.shorturl;

import com.demo.shorturl.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShortUrlApplicationTests {

	@Autowired
	private ShortUrlService shortUrlServiceImpl;

	@Test
	void shortUrlTest() {
		try {

			String shortUrl = shortUrlServiceImpl.getShortUrl("http://www.baidu.com", 1);
			System.out.println(shortUrlServiceImpl.getLongUrl(shortUrl));
			System.out.println(shortUrlServiceImpl.getLongUrl(null));
			System.out.println(shortUrlServiceImpl.getLongUrl(""));
			shortUrlServiceImpl.getShortUrl("http://www.baidu.com", 1);
            shortUrlServiceImpl.getShortUrl("http://www.google.com", -1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
