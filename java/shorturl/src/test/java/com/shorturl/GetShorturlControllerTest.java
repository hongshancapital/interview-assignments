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

import com.shorturl.entity.ResponseDto;


/**
 * 获取短域名测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetShorturlControllerTest{
	private static final Logger logger = LoggerFactory.getLogger(GetShorturlControllerTest.class);
	@Autowired
	private TestRestTemplate template;

	@Test
	public void getShorturl() {
		//先认证
		ResponseDto  responseDto = template.getForObject("/get_token/?url=admin", ResponseDto.class);
		Object data =  template.getForObject("/shorturl/?url=www.baidu.com&access_token="+responseDto.getData(), String.class);
		this.print("获取到的短域名:", data);
	}
	public void print(String desc,Object object) {
		logger.info(desc+object.toString());
	}
}
