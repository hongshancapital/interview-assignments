package com.example.demo;

import com.example.demo.controller.HelloController;
import com.example.demo.controller.QueryController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private HelloController testControl;
	@Autowired
	private QueryController queryController;

	@Test
	void contextLoads() {
		String result = testControl.hello();
		System.out.println(result);
		System.out.println("hello junit!");
		String write_id = "write";
		String read_id = "read";
		String url = "wanghaitao";
		String shortUrl = "aeb0cd00";
		String ret_shortUrl = queryController.query(write_id, url);
		Assert.assertEquals(shortUrl, ret_shortUrl);
		//Assert.assertEquals(" ", ret_shortUrl);
		System.out.println(String.format("url=%s => shortUrl=%s", url, ret_shortUrl));


		String ret_url = queryController.query(read_id, shortUrl);
		Assert.assertEquals(url, ret_url);
		System.out.println(String.format("shortUrl=%s => url=%s", shortUrl, ret_url));

		ret_shortUrl = queryController.query(write_id, url + "aaa");
		ret_shortUrl = queryController.query(write_id, url + "bbb");
		ret_shortUrl = queryController.query(write_id, url + "ccc");
		ret_shortUrl = queryController.query(write_id, url + "ddd");
		ret_shortUrl = queryController.query(write_id, url + "eee");
		ret_shortUrl = queryController.query(write_id, url + "eee");
		ret_shortUrl = queryController.query(write_id, url + "fff");
		ret_shortUrl = queryController.query(write_id, url + "fff");
		ret_shortUrl = queryController.query(write_id, url + "ggg");

		String ret_empty = queryController.query("***", shortUrl);
		System.out.println(String.format("shortUrl=%s => url=%s", shortUrl, ret_empty));
	}

}
