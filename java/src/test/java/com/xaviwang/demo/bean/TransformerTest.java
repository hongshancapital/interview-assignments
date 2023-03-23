/**
 * 
 */
package com.xaviwang.demo.bean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author xaviwang
 *
 */
class TransformerTest {
	Transformer transformer;
	String originalUrl;
	String tinyUrl;
	String expectedDefaultCompleteTinyUrl;
	String expectedRealTinyUrl;
	int expectedMaxId;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		transformer = new Transformer();
		originalUrl = "www.test.com";
		tinyUrl = "0";
		expectedDefaultCompleteTinyUrl = "www.tinyurl.com/########";
		expectedRealTinyUrl = "00000000";
		expectedMaxId = 0;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.xaviwang.demo.bean.Transformer#Transformer()}.
	 */
	@Test
	void testTransformer() {
		Assertions.assertNotNull(transformer.getMapFromOriginalUrlToTinyUrl());
		Assertions.assertNotNull(transformer.getMapFromTinyUrlToOriginalUrl());
		int realResult = transformer.getMaxId();
		Assertions.assertTrue(expectedMaxId == realResult);
	}

	/**
	 * Test method for {@link com.xaviwang.demo.bean.Transformer#getMapFromOriginalUrlToTinyUrl()}.
	 */
	@Test
	void testGetMapFromOriginalUrlToTinyUrl() {
		Assertions.assertNotNull(transformer.getMapFromOriginalUrlToTinyUrl());
	}

	/**
	 * Test method for {@link com.xaviwang.demo.bean.Transformer#getMapFromTinyUrlToOriginalUrl()}.
	 */
	@Test
	void testGetMapFromTinyUrlToOriginalUrl() {
		Assertions.assertNotNull(transformer.getMapFromTinyUrlToOriginalUrl());
	}

	/**
	 * Test method for {@link com.xaviwang.demo.bean.Transformer#getMaxId()}.
	 */
	@Test
	void testGetMaxId() {
		int realResult = transformer.getMaxId();
		Assertions.assertTrue(expectedMaxId == realResult);
	}

	/**
	 * Test method for {@link com.xaviwang.demo.bean.Transformer#updateMaxId()}.
	 */
	@Test
	void testUpdateMaxId() {
		transformer.updateMaxId();
		int realResult = transformer.getMaxId();
		int newExpectedMaxId = expectedMaxId + 1;
		Assertions.assertTrue(newExpectedMaxId == realResult);
	}

	/**
	 * Test method for {@link com.xaviwang.demo.bean.Transformer#makeUpToEightBits(java.lang.String)}.
	 */
	@Test
	void testMakeUpToEightBits() {
		String realResult = transformer.makeUpToEightBits(tinyUrl);
		Assertions.assertEquals(expectedRealTinyUrl, realResult);
	}

	/**
	 * Test method for {@link com.xaviwang.demo.bean.Transformer#transformFromOriginalUrlToTinyUrl(java.lang.String)}.
	 */
	@Test
	void testTransformFromOriginalUrlToTinyUrl() {
		String realResult = transformer.transformFromOriginalUrlToTinyUrl(originalUrl);
		Assertions.assertEquals(expectedDefaultCompleteTinyUrl, realResult);
	}

	/**
	 * Test method for {@link com.xaviwang.demo.bean.Transformer#transformFromTinyUrlToOriginalUrl(java.lang.String)}.
	 */
	@Test
	void testTransformFromTinyUrlToOriginalUrl() {
        transformer.transformFromOriginalUrlToTinyUrl(originalUrl);
        String realResult = transformer.transformFromTinyUrlToOriginalUrl(expectedDefaultCompleteTinyUrl);
        Assertions.assertEquals(originalUrl, realResult);
	}

}
