package com.example.sequoia;

import com.example.sequoia.controller.Domain;
import com.example.sequoia.service.DomainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void apiTest() {
		Domain domain = new Domain();
		domain.setService(new DomainService());

		Assertions.assertEquals(domain.shortLink(""), "");
		Assertions.assertEquals(domain.longLink(""), "");

		String longUrl = "https://www.baidu.com";
		String encodeUrl = "Ee79ti";

		String shortLink1 = domain.shortLink(longUrl);
		Assertions.assertEquals(shortLink1, encodeUrl);

		String shortLink2 = domain.shortLink(longUrl);
		Assertions.assertEquals(shortLink2, encodeUrl);

		String longLink1 = domain.longLink(encodeUrl);
		Assertions.assertEquals(longLink1, longUrl);

		String longLink2 = domain.longLink("ABCDEFG");
		Assertions.assertEquals(longLink2, "");
	}
}
