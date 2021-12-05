package com.example.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseSpringBootTest {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void init() {
		logger.info("开始测试...");
	}

	@After
	public void after() {
		logger.info("测试结束...");
	}

	@Test
	public void test(){
		logger.info("test start");
	}
}
