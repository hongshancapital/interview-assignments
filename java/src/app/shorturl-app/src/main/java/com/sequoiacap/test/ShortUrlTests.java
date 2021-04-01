package com.sequoiacap.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.sequoiacap.cache.utils.JedisUtil;
import com.sequoiacap.errorcode.ErrorCode;
import com.sequoiacap.exception.BusinessException;
import com.sequoiacap.shorturl.model.SUrl;
import com.sequoiacap.shorturl.model.SUrl.Type;
import com.sequoiacap.shorturl.service.SUrlManager;
import com.sequoiacap.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

@Component
public class ShortUrlTests
	implements InitializingBean, ApplicationContextAware
{
	private static final Logger log = Logger.getLogger(ShortUrlTests.class);

	@Value("${test:false}")
	private Boolean isTest;

	@Autowired
	private JedisUtil jedisUtil;
	
	@Autowired
	private SUrlManager sUrlManager;

	private ApplicationContext appCtxt;
	
	static final String testUrl = "https://test.baidu.com?code=";

	private String url1, url2, url3;
	private String shortUrl1, shortUrl2, shortUrl3;
	
	@Test
	void testSet()
	{
		url1 = testUrl + Utils.randomId();
		url2 = testUrl + Utils.randomId();
		url3 = testUrl + Utils.randomId();

		SUrl surl1 = sUrlManager.set(url1, SUrl.Type.temporary, "0.0.0.0");
		Assert.assertNotNull(surl1);
		Assert.assertFalse(StringUtils.isBlank(surl1.getShortUrl()));
		Assert.assertEquals(url1, surl1.getUrl());
		Assert.assertEquals(SUrl.Type.temporary, surl1.getType());

		SUrl surl2 = sUrlManager.set(url2, SUrl.Type.short_period, "0.0.0.0");
		Assert.assertNotNull(surl2);
		Assert.assertFalse(StringUtils.isBlank(surl2.getShortUrl()));
		Assert.assertEquals(url2, surl2.getUrl());
		Assert.assertEquals(SUrl.Type.short_period, surl2.getType());

		SUrl surl3 = sUrlManager.set(url3, SUrl.Type.permanent, "0.0.0.0");
		Assert.assertNotNull(surl3);
		Assert.assertFalse(StringUtils.isBlank(surl3.getShortUrl()));
		Assert.assertEquals(url3, surl3.getUrl());
		Assert.assertEquals(SUrl.Type.permanent, surl3.getType());

		shortUrl1 = surl1.getShortUrl();
		shortUrl2 = surl2.getShortUrl();
		shortUrl3 = surl3.getShortUrl();

		try
		{
			SUrl nullUrl = sUrlManager.set(null, Type.temporary, null);
			Assert.assertTrue(false);
		} catch(BusinessException e)
		{
			Assert.assertEquals(e.getStatus(), ErrorCode.INVALID_URL);
		}
		
		try
		{
			SUrl nullUrl = sUrlManager.set(url1, null, null);
			Assert.assertTrue(false);
		} catch(BusinessException e)
		{
			Assert.assertEquals(e.getStatus(), ErrorCode.INVALID_TYPE);
		}
		
		try
		{
			SUrl nullUrl = sUrlManager.set(
				url1 + Utils.getRandomStringByLength(65535),
				Type.temporary, null);
			Assert.assertTrue(false);
		} catch(BusinessException e)
		{
			Assert.assertEquals(e.getStatus(), ErrorCode.TOO_LONG_URL);
		}

		SUrl cacheUrl = sUrlManager.set(url1, SUrl.Type.temporary, "0.0.0.0");
		Assert.assertNotNull(cacheUrl);
		Assert.assertEquals(cacheUrl.getUrl(), url1);
		Assert.assertEquals(cacheUrl.getShortUrl(), surl1.getShortUrl());

		SUrl upgrade = sUrlManager.set(url2,  SUrl.Type.permanent, "0.0.0.0");
		Assert.assertNotNull(upgrade);
		Assert.assertEquals(upgrade.getUrl(), url2);
		Assert.assertEquals(upgrade.getShortUrl(), surl2.getShortUrl());
		Assert.assertEquals(upgrade.getType(), SUrl.Type.permanent);
	}

	@Test
	public void testGet()
	{
		SUrl surl1 = sUrlManager.get(shortUrl1);
		Assert.assertNotNull(surl1);
		Assert.assertEquals(url1, surl1.getUrl());

		SUrl surl2 = sUrlManager.get(shortUrl2);
		Assert.assertNotNull(surl2);
		Assert.assertEquals(url2, surl2.getUrl());

		SUrl surl3 = sUrlManager.get(shortUrl3);
		Assert.assertNotNull(surl3);
		Assert.assertEquals(url3, surl3.getUrl());

		String url4 = testUrl + Utils.randomId();
		SUrl surl4 = sUrlManager.get(url4);
		Assert.assertNull(surl4);

		jedisUtil.del("surl:" + shortUrl1);

		surl1 = sUrlManager.get(shortUrl1);
		Assert.assertNull(surl1);
	}
	
	@Override
	public void afterPropertiesSet()
		throws Exception
	{
		if (!isTest.booleanValue())
			return;

		log.info("start test");

		testSet();
		testGet();

		log.info("finish test");

		((ConfigurableApplicationContext) appCtxt).close();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException
	{
		appCtxt = applicationContext;
	}
}
