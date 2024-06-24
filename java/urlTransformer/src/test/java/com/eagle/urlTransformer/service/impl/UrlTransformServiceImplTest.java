package com.eagle.urlTransformer.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.eagle.urlTransformer.service.UrlTransformService;
import com.eagle.urlTransformer.util.UrlCache;

@SpringBootTest
class UrlTransformServiceImplTest {

	@Autowired
	private UrlTransformService transService;
	
	@Test
	void testSaveLongUrl() {
		assertNotNull(transService);
		String longTestUrl = "http://xxx.qqqqqqqqqqqqqqqqqwwwwwwwwwwwwwxxxxxxxxx.com/123/qwe?name=eagle";
		String shortUrl = transService.saveLongUrl(longTestUrl);
		//长度小于8的判断
		assertTrue(StringUtils.hasLength(shortUrl)&&shortUrl.length()<=8);
		//重复存入场景
		String shortUrl2 = transService.saveLongUrl(longTestUrl);
		assertEquals(shortUrl,shortUrl2);
		
		String longUrlByShortUrl = transService.getLongUrlByShortUrl(shortUrl);
		assertEquals(longTestUrl,longUrlByShortUrl);
	}

	/** 
	 * @Title: testDuplicateShortUrl 
	 * @Description:测试md5生成的四个短链接全部重复，兜底生成shortUrl方案
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws 
	 */ 
	@Test
	void testDuplicateShortUrl() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = UrlTransformServiceImpl.class.getDeclaredMethod("genShortUrl", new Class[]{String.class});
		method.setAccessible(true);
		String longTestUrl = "http://xxx.oooooowwwwwwwwwwwwwxxxxxxxxx.com/123/qwe?name=eagle";
		String[] shortUrlArr = (String[])method.invoke(new UrlTransformServiceImpl(), longTestUrl);
		System.out.println(shortUrlArr.length);
		int dummyKey = 0;
		//将生成的四个备用短链接全都模拟使用，让代码走到兜底分支
		for(String url : shortUrlArr) {
			UrlCache.putUrlPair(new String[] {url}, dummyKey+"");
		}
		String shortUrl = transService.saveLongUrl(longTestUrl);
		System.out.println("================>"+shortUrl);
		assertTrue(StringUtils.hasLength(shortUrl)&&shortUrl.length()<=8);
		
		String getLongUrl = transService.getLongUrlByShortUrl(shortUrl);
		assertEquals(longTestUrl,getLongUrl);
	}
	
	@Test
	void testSaveLongUrlNullParam() {
		assertNotNull(transService);
		String longTestUrl = "";
		String shortUrl = transService.saveLongUrl(longTestUrl);
		assertNull(shortUrl);
	}
	
	@Test
	void testgetLongUrlNullParam() {
		assertNotNull(transService);
		String shortTestUrl = "";
		String longUrl = transService.getLongUrlByShortUrl(shortTestUrl);
		assertNull(longUrl);
	}

}
