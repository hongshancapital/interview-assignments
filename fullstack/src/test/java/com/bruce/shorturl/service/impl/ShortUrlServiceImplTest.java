package com.bruce.shorturl.service.impl;

import com.bruce.shorturl.data.TestData;
import com.bruce.shorturl.exception.ErrorCode;
import com.bruce.shorturl.exception.GenericException;
import com.bruce.shorturl.exception.GenericRuntimeException;
import com.bruce.shorturl.service.IShortUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlServiceImplTest {

	@Autowired
	private IShortUrlService shortUrlService;

	@Test
	public void generateShortUrl1() {
//		String result = shortUrlService.generateShortUrl(null);
		GenericRuntimeException exception = Assertions.assertThrows(
				GenericRuntimeException.class, () -> shortUrlService.generateShortUrl(null));

	}


	@Test
	public void generateShortUrl2() {
//		shortUrlService.generateShortUrl("");
		GenericRuntimeException exception = Assertions.assertThrows(
				GenericRuntimeException.class, () -> shortUrlService.generateShortUrl(""));

	}

	@Test
	public void generateShortUrl3() {
		GenericRuntimeException exception = Assertions.assertThrows(
				GenericRuntimeException.class, () -> shortUrlService.generateShortUrl(" "));
		Assertions.assertEquals(ErrorCode.ERROR_CODE_SHORTURL_PARAM,  exception.getErrCode());
	}


	@Test
	public void generateShortUrl4() {
		String result = shortUrlService.generateShortUrl(TestData.DEFAULT_HASH_VALUE);
		Assertions.assertNotNull(result);
	}


	@Test
	void generateShortUrl10() {
		GenericRuntimeException exception = Assertions.assertThrows(
				GenericRuntimeException.class, () -> shortUrlService.generateShortUrl(TestData.DEFAULT_HASH_VALUE, null, 6));
		Assertions.assertEquals(ErrorCode.ERROR_CODE_SHORTURL,  exception.getErrCode());
	}

	@Test
	public void generateShortUrl11() {
		String result = shortUrlService.generateShortUrl(TestData.DEFAULT_HASH_VALUE, "_", 5);
		Assertions.assertNotNull(result);
	}



	@Test
	void loadFullUrlByHash1() {
		GenericException exception = Assertions.assertThrows(
				GenericException.class, () -> shortUrlService.loadFullUrlByHash(null));
		Assertions.assertEquals(ErrorCode.ERROR_CODE_SHORTURL_NOT_EXISTS,  exception.getErrCode());
	}

	@Test
	void loadFullUrlByHash2() {
		GenericException exception = Assertions.assertThrows(
				GenericException.class, () -> shortUrlService.loadFullUrlByHash("11111"));
		Assertions.assertEquals(ErrorCode.ERROR_CODE_SHORTURL_NOT_EXISTS,  exception.getErrCode());
	}
}