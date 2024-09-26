package com.example.tinyurl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Tiny Url Controller tests in local development environment with database setup
 * @author hermitriver
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TinyUrlControllerInDevTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	/** Test to load spring context */
	@Test
	void contextLoads() {
	}

	/** Test generate method in TinyUrlController (The database should be setup) */
	@Test
	void testGenerate() throws Exception {
		String requestResult = this.restTemplate.getForObject("http://127.0.0.1:" + port + "/_generate?t=http://www.baidu.com", String.class);
		Assertions.assertThat(requestResult).hasSizeLessThan(9).hasSizeGreaterThan(0);
	}
}
