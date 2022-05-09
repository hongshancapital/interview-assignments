package com.example.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LinkConvertApplicationTest {

	@Autowired
	RestTemplate restTemplate;

	@Test
	public void convertToLongLink() {
		String url = "http://www.baidu.com";
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/linkEnter/convertToLongLink?url=" + url, String.class);
		assertEquals("tt.cn/a/003Spuec", entity.getBody());
	}

	@Test
	public void getLongLink() {
		String shortUrl = "003Spuec";
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/linkEnter/getLongLink?shortUrl=" + shortUrl, String.class);
		assertEquals("http://www.baidu.com", entity.getBody());
	}

}
