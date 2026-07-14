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
import org.springframework.util.Assert;

import com.shorturl.entity.ResponseDto;


/**
 * 获取长域名测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetLongurlControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(GetLongurlControllerTest.class);
	@Autowired
	private TestRestTemplate template;
	@Test
	public void getLongurl() {
		//获取签名
		ResponseDto  responseDto = template.getForObject("/get_token/?url=admin", ResponseDto.class);
		//获取短域名
		StringBuffer getsurl = new StringBuffer("/shorturl/?url=www.baidu.com&access_token=").append(responseDto.getData());
		ResponseDto shorturl =  template.getForObject(getsurl.toString(), ResponseDto.class);
		this.print("获取到的短域名:", shorturl.getData());
		//根据获取到的短域名获取长域名
		StringBuffer getlurl = new StringBuffer("/longurl/?access_token=").append(responseDto.getData()).append("&url=").append(shorturl.getData());
		ResponseDto longurl =  template.getForObject(getlurl.toString(), ResponseDto.class);
		this.print("获取到的长域名:", longurl.getData());
		Assert.isTrue("www.baidu.com".equals(longurl.getData().toString()),"短域名反向转换不一致");
		//签名反向测试
		Object data1 =  template.getForObject("/shorturl/?url=www.baidu.com&access_token=6666", String.class);
		this.print("签名测试:", data1);
	}
	public void print(String desc,Object object) {
		logger.info(desc+object.toString());
	}
}
