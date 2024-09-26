package com.xxw.domain;

import com.xxw.domain.service.ShortUrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanControllerTest {
	private TestRestTemplate testRestTemplate = new TestRestTemplate();

	@Test
	public void testController(){
		String shortUrl = "http://localhost:8080/domain/short?url=www.hao123.com";

		String shortUrlResult = testRestTemplate.postForObject(shortUrl,null,String.class);
		assertNotNull(shortUrlResult);
		assertTrue(shortUrlResult != null);

		System.out.println("接受长域名信息，返回短域名信息-输出结果： "+shortUrlResult);

		String longUrl = "http://localhost:8080/domain/long?url="+shortUrlResult;

		String longUrlResult = testRestTemplate.postForObject(longUrl,null,String.class);
		assertNotNull(longUrlResult);
		assertTrue(longUrlResult != null);
		System.out.println("接受短域名信息，返回长域名信息-输出结果： "+longUrlResult);


	}
}