package com.scdt.interview.leon.test;

import com.scdt.interview.leon.service.OverdueCache;
import com.scdt.interview.leon.service.UrlService;
import com.scdt.interview.leon.spec.MConstants;
import com.scdt.interview.leon.spec.MException;
import com.scdt.interview.leon.util.ConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 单元测试类
 *
 * @author leon
 * @since 2021/10/26
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class UnitTest {

	private static String shortUrl = "";

	private final String oriUrl = "https://sports.sina.com.cn/china/";

	@Autowired
	private UrlService urlService;

	@BeforeEach
	public void setUp() {
	}

	/*
	 * 62进制转换测试
	 */
	@Test
	@Order(1)
	public void conversionTest() {

		String base62 = ConversionUtil.encode(14876388L, 8);

		long base10 = ConversionUtil.decode(base62);

		assertEquals("00010Q1k", base62);
		assertEquals(14876388L, base10);

		log.info("shortUrl = "+ shortUrl);
	}

	/*
	 * 缓存测试
	 */
	@Test
	@Order(2)
	public void cacheTest() {
		try{
			OverdueCache<String, String> urlCache = new OverdueCache<>(4, 3000);

			urlCache.put("001", oriUrl);
			urlCache.put("002", oriUrl);
			urlCache.put("003", oriUrl);
			urlCache.put("004", oriUrl);
			assertEquals(4, urlCache.size());

			//缓存容量超限测试
			Throwable me = assertThrows(IndexOutOfBoundsException.class, () -> urlCache.put("005", oriUrl));

			//缓存过期测试
			Thread.sleep(4000);
			log.info(urlCache.get("001"));
			assertNull(urlCache.get("001"));

			//过期后容量测试
			urlCache.put("006", oriUrl);
			assertEquals(1, urlCache.size());
			assertEquals(oriUrl, urlCache.get("006"));

		}catch (Exception e){
			log.info("error",e);
		}
	}

	/*
	 * 短链测试
	 */
	@Test
	@Order(11)
	public void shortenTest() {

		shortUrl = urlService.shorten(oriUrl);

		assertNotNull(shortUrl);
		assertEquals(8, shortUrl.length());

		log.info("shortUrl = "+ shortUrl);
	}

	/*
	 * 恢复长链测试
	 */
	@Test
	@Order(12)
	public void recoverTest() {

		String recoverUrl = urlService.recover(shortUrl);

		assertNotNull(recoverUrl);
		assertEquals(oriUrl, recoverUrl);

		log.info("oriUrl = "+ recoverUrl);
	}

	/*
	 * 异常测试
	 */
	@Test
	@Order(21)
	public void notfindExceptionTest() {
		Throwable me = assertThrows(MException.class, () -> urlService.recover("123456789"));

		assertEquals("没有找到对应的长链接，请检查输入", me.getMessage());
	}

	/*
	 * 短链超限测试
	 */
	@Test
	@Order(22)
	public void overflowExceptionTest() {
		MConstants.URL_LENGTH_MAX = 2;
		Throwable me = assertThrows(MException.class, () -> urlService.shorten(oriUrl));

		assertEquals("服务器内部错误，请稍后再试", me.getMessage());
		MConstants.URL_LENGTH_MAX = 8;
	}
}
