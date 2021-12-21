package com.liuhuachao.shorturl.util; 

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* StoreUtil Tester. 
* 
* @author <liuhuachao> 
* @since <pre>12/21/2021</pre> 
* @version 1.0 
*/
public class StoreUtilTest {

	/**
	 * 短域名
	 */
	private static final String SHORT_URL = "x.y/1W";

	/**
	 * 原始域名
	 */
	private static final String ORIGIN_URL = "http://www.liuhuachao.com/job";

	@Before
	public void before() throws Exception {
		StoreUtil.store(SHORT_URL,ORIGIN_URL);
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * Method: store(String shortUrl, String originUrl)
	 */
	@Test
	public void testStore() throws Exception {
		StoreUtil.store(SHORT_URL,ORIGIN_URL);
		Assert.assertNotEquals(0,StoreUtil.SHORT_ORIGIN_URL_MAP.size());
		Assert.assertNotEquals(0,StoreUtil.ORIGIN_SHORT_URL_MAP.size());
	}

	/**
	 * Method: getShortUrl(String originUrl)
	 */
	@Test
	public void testGetShortUrl() throws Exception {

		String actualShortUrl = StoreUtil.getShortUrl(ORIGIN_URL);
		Assert.assertEquals(SHORT_URL,actualShortUrl);
	}

	/**
	 * Method: getOriginUrl(String shortUrl)
	 */
	@Test
	public void testGetOriginUrl() throws Exception {
		String actualOriginUrl = StoreUtil.getOriginUrl(SHORT_URL);
		Assert.assertEquals(ORIGIN_URL,actualOriginUrl);
	}

} 
