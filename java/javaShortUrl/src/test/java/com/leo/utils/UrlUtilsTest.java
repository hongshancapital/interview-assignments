package com.leo.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class UrlUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testHasProtocal() {
		Assert.assertFalse(UrlUtils.hasProtocal("abc"));
		Assert.assertFalse(UrlUtils.hasProtocal("://abc"));
		Assert.assertTrue(UrlUtils.hasProtocal("http://www.baidu.com"));
		Assert.assertTrue(UrlUtils.hasProtocal("https://www.baidu.com:9000"));
		Assert.assertTrue(UrlUtils.hasProtocal("http://www.baidu.com:9000/ABC/CCC"));
	}

	@Test
	public void testIsURI() {
//		fail("Not yet implemented");
		Assert.assertFalse(UrlUtils.isURI(""));
		Assert.assertTrue(UrlUtils.isURI("http://www.baidu.com"));
		Assert.assertTrue(UrlUtils.isURI("http://www.baidu.com"));
		Assert.assertTrue(UrlUtils.isURI("http://www.baidu.com:9000"));
		Assert.assertTrue(UrlUtils.isURI("http://www.baidu.com:9000/ABC/CCC"));
	}

	@Test
	public void testUrlToLowerCase() {
		String u1 = "Http://www.BAIDU.com:9000";
		String u2 = "Http://www.BAIDU.com:9000/ABC";
		String u3 = "Http://www.BAIDU.com:9000/ABC/CCC";
		Assert.assertEquals(UrlUtils.urlToLowerCase(u1), "http://www.baidu.com:9000");
		Assert.assertEquals(UrlUtils.urlToLowerCase(u2),"http://www.baidu.com:9000/ABC");
		Assert.assertEquals(UrlUtils.urlToLowerCase(u3),"http://www.baidu.com:9000/ABC/CCC");
	}
}
