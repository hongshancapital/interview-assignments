package com.xaviwang.demo.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xaviwang.demo.bean.IncrementalIdTransformer;
import com.xaviwang.demo.bean.RandomIdTransformer;
import com.xaviwang.demo.bean.SixtyTwoBitsIdTransformer;

class TransformControllerTest {
	IncrementalIdTransformer incrementalIdTransformer;
	RandomIdTransformer randomIdTransformer;
	SixtyTwoBitsIdTransformer sixtyTwoBitsIdTransformer;
	String originalLongUrl;
	String existedLongUrl;
	String tinyUrlOfIncrementalIdTransformer;
	String tinyUrlOfRandomIdTransformer;
	String tinyUrlOfSixtyTwoBitsIdTransformer;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		incrementalIdTransformer = new IncrementalIdTransformer();
		randomIdTransformer = new RandomIdTransformer();
		sixtyTwoBitsIdTransformer = new SixtyTwoBitsIdTransformer();
		originalLongUrl = "www.test.com";
		existedLongUrl = "www.existed.com";
		tinyUrlOfIncrementalIdTransformer = incrementalIdTransformer.transformFromOriginalUrlToTinyUrl(existedLongUrl);
		tinyUrlOfRandomIdTransformer = randomIdTransformer.transformFromOriginalUrlToTinyUrl(existedLongUrl);
		tinyUrlOfSixtyTwoBitsIdTransformer = sixtyTwoBitsIdTransformer.transformFromOriginalUrlToTinyUrl(existedLongUrl);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testIncrementalIdTransformFromLongUrlToShortUrl() {

	}

	@Test
	void testIncrementalIdTransformFromShortUrlToLongUrl() {

	}

	@Test
	void testSixtyTwoBitsIdTransformFromLongUrlToShortUrl() {

	}

	@Test
	void testSixtyTwoBitsIdTransformFromShortUrlToLongUrl() {

	}

	@Test
	void testRandomIdTransformFromLongUrlToShortUrl() {

	}

	@Test
	void testRandomIdTransformFromShortUrlToLongUrl() {

	}

}
