package com.leo.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.leo.ShortUrlApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ShortUrlApplication.class })

public class AppConfigTest {

	@Autowired
	private AppConfig appConfig;

	@Test
	public void testGetUrlMaxLength() {
		assertEquals(512, appConfig.getUrlMaxLength());
	}

//	@Test
//	public void testSetUrlMaxLength() {
//		appConfig.setUrlMaxLength(10);
//		assertEquals(10, appConfig.getUrlMaxLength());
//	}
}
