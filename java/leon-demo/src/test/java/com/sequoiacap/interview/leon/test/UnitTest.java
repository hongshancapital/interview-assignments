package com.sequoiacap.interview.leon.test;

import com.sequoiacap.interview.leon.service.UrlService;
import com.sequoiacap.interview.leon.spec.MConstants;
import com.sequoiacap.interview.leon.spec.MException;
import com.sequoiacap.interview.leon.util.ConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

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

	@Test
	@Order(1)
	public void conversionTest() {

		String base62 = ConversionUtil.encode(14876388L, 8);

		long base10 = ConversionUtil.decode(base62);

		assertEquals("00010Q1k", base62);
		assertEquals(14876388L, base10);

		log.info("shortUrl = "+ shortUrl);
	}

	@Test
	@Order(11)
	public void shortenTest() {

		shortUrl = urlService.shorten(oriUrl);

		assertNotNull(shortUrl);
		assertEquals(8, shortUrl.length());

		log.info("shortUrl = "+ shortUrl);
	}

	@Test
	@Order(12)
	public void recoverTest() {

		String recoverUrl = urlService.recover(shortUrl);

		assertNotNull(recoverUrl);
		assertEquals(oriUrl, recoverUrl);

		log.info("oriUrl = "+ recoverUrl);
	}

	@Test
	@Order(21)
	public void notfindExceptionTest() {
		Throwable me = assertThrows(MException.class, () -> urlService.recover("123456789"));

		assertEquals("没有找到对应的长链接，请检查输入", me.getMessage());
	}

	@Test
	@Order(22)
	public void overflowExceptionTest() {
		MConstants.URL_LENGTH_MAX = 2;
		Throwable me = assertThrows(MException.class, () -> urlService.shorten(oriUrl));

		assertEquals("服务器内部错误，请稍后再试", me.getMessage());
		MConstants.URL_LENGTH_MAX = 8;
	}
}
