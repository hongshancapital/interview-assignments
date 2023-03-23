package com.xaviwang.demo.bean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncrementalIdTransformerTest {
	IncrementalIdTransformer transformer;
	String originalUrl;
	String tinyUrl;
	String expectedDefaultCompleteTinyUrl;
	int expectedMaxId;
	String existedOriginalUrl;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		transformer = new IncrementalIdTransformer();
		originalUrl = "www.test.com";
		tinyUrl = "1";
		expectedDefaultCompleteTinyUrl = "www.tinyurl.com/00000001";
		expectedMaxId = 1;
		existedOriginalUrl = "www.existed.com";
		transformer.transformFromOriginalUrlToTinyUrl(existedOriginalUrl);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTransformFromOriginalUrlToTinyUrl_AlreadyExisted() {
		String realResult = transformer.transformFromOriginalUrlToTinyUrl(existedOriginalUrl);
		Assertions.assertNotNull(realResult);
	}

	@Test
	void testTransformFromOriginalUrlToTinyUrl_NotExisted() {
		Assertions.assertNull(transformer.getMapFromOriginalUrlToTinyUrl().get(originalUrl));
		String realResult = transformer.transformFromOriginalUrlToTinyUrl(originalUrl);
		Assertions.assertEquals(expectedDefaultCompleteTinyUrl, realResult);
		Assertions.assertNotNull(transformer.getMapFromOriginalUrlToTinyUrl().get(originalUrl));
		Assertions.assertNotNull(transformer.getMapFromTinyUrlToOriginalUrl().get(expectedDefaultCompleteTinyUrl));
	}

	@Test
	void testIncrementalIdTransformer() {
		Assertions.assertNotNull(transformer.getMapFromOriginalUrlToTinyUrl());
		Assertions.assertNotNull(transformer.getMapFromTinyUrlToOriginalUrl());
		int realResult = transformer.getMaxId();
		Assertions.assertTrue(expectedMaxId == realResult);
	}

}
