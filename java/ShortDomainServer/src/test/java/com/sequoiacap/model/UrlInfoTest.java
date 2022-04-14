package com.sequoiacap.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author zoubin
 *
 */
public class UrlInfoTest {

	@Test
	public void testHashCode() {
		Assert.assertTrue(new UrlInfo().hashCode() > 0);
	}

	@Test
	public void testGetCode() {
		Assert.assertEquals(200, new UrlInfo(200, "", "").getCode());
	}

	@Test
	public void testSetCode() {
		UrlInfo u = new UrlInfo();
		u.setCode(200);
		Assert.assertEquals(200, u.getCode());
	}

	@Test
	public void testGetUrl() {
		Assert.assertEquals("http://www.baidu.com", new UrlInfo(200, "http://www.baidu.com", "").getUrl());

	}

	@Test
	public void testSetUrl() {
		UrlInfo u = new UrlInfo();
		u.setUrl("http://www.baidu.com");
		Assert.assertEquals("http://www.baidu.com", u.getUrl());
	}

	@Test
	public void testGetErrorMsg() {
		Assert.assertEquals("", new UrlInfo(200, "http://www.baidu.com", "").getErrorMsg());
	}

	@Test
	public void testSetErrorMsg() {
		UrlInfo u = new UrlInfo();
		u.setErrorMsg("error");
		Assert.assertEquals("error", u.getErrorMsg());
	}
}
