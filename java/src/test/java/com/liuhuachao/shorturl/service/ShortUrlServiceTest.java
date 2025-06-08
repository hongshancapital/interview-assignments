package com.liuhuachao.shorturl.service; 

import com.liuhuachao.shorturl.util.StoreUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import sun.security.util.Password;

/**
 * ShortUrlService Tester.
 * @author <liuhuachao>
 * @version 1.0
 * @since <pre>12/21/2021</pre>
 */
public class ShortUrlServiceTest {

	/**
	 * 短域名
	 */
	private String SHORT_URL = "x.y/1W";

	/**
	 * 非法的短域名
	 */
	private String  illegalUrl1 = "xyz";

	/**
	 * 原始域名
	 */
	private static final String ORIGIN_URL = "http://www.liuhuachao.com/job";

	@Before
	public void before() throws Exception {
		String shortUrl = SHORT_URL.replaceAll(ShortUrlService.PREFIX,"");
		StoreUtil.store(shortUrl,ORIGIN_URL);
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * Method: getShortUrl(String originUrl)
	 */
	@Test
	public void testGetShortUrl() throws Exception {
		String actualShortUrl = ShortUrlService.getShortUrl(ORIGIN_URL);
		Assert.assertEquals(SHORT_URL,actualShortUrl);
	}

	/**
	 * Method: getOriginUrl(String shortUrl)
	 */
	@Test
	public void testGetOriginUrl() throws Exception {
		String actualOriginUrl = ShortUrlService.getOriginUrl(SHORT_URL);
		Assert.assertEquals(ORIGIN_URL,actualOriginUrl);
	}

	/**
	 * Method: checkUrl(String url, Boolean isShortUrl)
	 */
	@Test
	public void testCheckUrl() throws Exception {
		try{
			ShortUrlService.checkUrl(illegalUrl1,true );
		}catch(Exception ex){
			Assert.assertTrue(ex.getMessage().contains("不是合法的Url"));
		}
	}

	/**
	 * Method: isValid(String url, Boolean isShortUrl)
	 */
	@Test
	public void testIsValid() throws Exception {
		boolean isUrl1Valid = ShortUrlService.isValid(illegalUrl1, true);
		Assert.assertFalse("短域名不合法",isUrl1Valid);

		boolean isUrl2Valid = ShortUrlService.isValid(ORIGIN_URL, false);
		Assert.assertTrue("短域名不合法",isUrl2Valid);
	}

} 
