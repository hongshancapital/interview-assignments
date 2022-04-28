package com.example.urlshortener

import org.assertj.core.api.Assertions.assertThat
import com.example.urlshortener.controller.ShortUrlController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTests {

	@Autowired
	private val controller: ShortUrlController? = null

	@Test
	fun contextLoads() {
		assertThat(controller).isNotNull;
	}
}
