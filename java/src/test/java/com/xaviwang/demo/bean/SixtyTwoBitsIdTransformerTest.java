package com.xaviwang.demo.bean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SixtyTwoBitsIdTransformerTest {
	SixtyTwoBitsIdTransformer transformer;
	String originalUrl;
	String tinyUrl;
	String expectedTinyUrl;
	long expectedMaxId;
	String existedOriginalUrl;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		transformer = new SixtyTwoBitsIdTransformer();
		originalUrl = "www.test.com";
		tinyUrl = "1";
		expectedTinyUrl = "www.tinyurl.com/00000001";
		expectedMaxId = 1L;
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
		Assertions.assertEquals(expectedTinyUrl, realResult);
		Assertions.assertNotNull(transformer.getMapFromOriginalUrlToTinyUrl().get(originalUrl));
		Assertions.assertNotNull(transformer.getMapFromTinyUrlToOriginalUrl().get(expectedTinyUrl));
	}

	@Test
	void testSixtyTwoBitsIdTransformer() {
		Assertions.assertNotNull(transformer.getMapFromOriginalUrlToTinyUrl());
		Assertions.assertNotNull(transformer.getMapFromTinyUrlToOriginalUrl());
		long realResult = transformer.getMaxLongId();
		Assertions.assertTrue(expectedMaxId == realResult);
	}
	
	@Test
	void testTransformLongUrlToSixtyTwoBitsString() {
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(0), "0");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(9), "9");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(10), "a");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(35), "z");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(36), "A");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(61), "Z");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(62), "10");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(3843), "ZZ");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(3844), "100");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(238327), "ZZZ");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(238328), "1000");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(14776335), "ZZZZ");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(14776336), "10000");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(916132831), "ZZZZZ");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(916132832), "100000");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(56800235583L), "ZZZZZZ");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(56800235584L), "1000000");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(3521614606207L), "ZZZZZZZ");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(3521614606208L), "10000000");
		Assertions.assertEquals(transformer.transformOriginalUrlToSixtyTwoBitsString(218340105584895L), "ZZZZZZZZ");
	}

}
