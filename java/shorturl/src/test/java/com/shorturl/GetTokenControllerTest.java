package com.shorturl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


/**
 *获取token测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetTokenControllerTest{
	private static final Logger logger = LoggerFactory.getLogger(GetTokenControllerTest.class);
	@Autowired
	private TestRestTemplate template;

	@Test
	public void getToken() {
	String token = template.getForObject("/get_token/?url=admin", String.class);
	this.print("token:",token);
	}
	public void print(String desc,Object object) {
		logger.info(desc+object.toString());
	}
}
