package com.xaviwang.demo.bean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomIdTransformerTest {
	RandomIdTransformer transformer;
	String originalUrl;
	String tinyUrl;
	String expectedTinyUrl;
	int expectedMaxId;
	String existedOriginalUrl;
	String existedTinylUrl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		transformer = new RandomIdTransformer();
		originalUrl = "www.test.com";
		existedOriginalUrl = "www.existed.com";
		existedTinylUrl = "www.tinyurl.com/########";
		transformer.getMapFromOriginalUrlToTinyUrl().put(existedOriginalUrl, existedTinylUrl);
		transformer.getMapFromTinyUrlToOriginalUrl().put(existedTinylUrl, existedOriginalUrl);
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
		transformer.transformFromOriginalUrlToTinyUrl(originalUrl);
		Assertions.assertNull(transformer.getMapFromTinyUrlToOriginalUrl().get(tinyUrl));
		Assertions.assertNotNull(transformer.getMapFromOriginalUrlToTinyUrl().get(originalUrl));
	}

	@Test
	void testRandomIdTransformer() {
		Assertions.assertNotNull(transformer.getMapFromOriginalUrlToTinyUrl());
		Assertions.assertNotNull(transformer.getMapFromTinyUrlToOriginalUrl());
	}

}
