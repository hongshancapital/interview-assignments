package com.example.shortUrl;

import com.example.shortUrl.utils.ShortUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ShortUrlApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testGenerator(){
		String longUrl = "https://github.com/sqskg/interview-assignments";
		log.info("short url = {}",ShortUrlGenerator.shortCodeGenerate(longUrl));
		//short url = VjzMBFIj
	}
}
