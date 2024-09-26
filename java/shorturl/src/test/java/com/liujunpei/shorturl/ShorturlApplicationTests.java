package com.liujunpei.shorturl;

import com.liujunpei.shorturl.controller.ShortUrlController;
import com.liujunpei.shorturl.model.RequestEntity;
import com.liujunpei.shorturl.model.ResponseEntity;
import com.liujunpei.shorturl.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import wiremock.org.apache.commons.lang3.StringUtils;

@SpringBootTest
class ShorturlApplicationTests {
	@Autowired
	private ShortUrlController shortUrlController;

	@Test
	public void testLongUrlToShortUrl() {
		String longUrl = "https://kubernetes.io/zh/docs/concepts/scheduling-eviction/taint-and-toleration/";
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setLongUrl(longUrl);
		ResponseEntity response = shortUrlController.longUrlToShortUrl(requestEntity);
		Assert.isTrue(StringUtils.isNotEmpty(response.getResult()), "success");
	}

	@Test
	public void testInvalidLongUrlToShortUrl() {
		String longUrl = "";
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setLongUrl(longUrl);
		ResponseEntity response = shortUrlController.longUrlToShortUrl(requestEntity);
		Assert.isTrue(response.getCode() == 500, "success");
	}

	@Test
	public void testGetShortUrlDuplicate() {
		String longUrl = "https://kubernetes.io/zh/docs/concepts/scheduling-eviction/taint-and-toleration/";
		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setLongUrl(longUrl);
		ResponseEntity response1 = shortUrlController.longUrlToShortUrl(requestEntity);
		ResponseEntity response2 = shortUrlController.longUrlToShortUrl(requestEntity);
		Assert.isTrue(response1.getResult().equals(response2.getResult()), "success");
	}

	@Test
	public void testShortUrlToLongUrl() {
		String longUrl = "https://kubernetes.io/zh/docs/concepts/scheduling-eviction/taint-and-toleration/";
		RequestEntity longToShortReq = new RequestEntity();
		longToShortReq.setLongUrl(longUrl);
		ResponseEntity shortUrlRes = shortUrlController.longUrlToShortUrl(longToShortReq);
		RequestEntity shortToLongReq = new RequestEntity();
		shortToLongReq.setShortUrl(shortUrlRes.getResult());
		ResponseEntity longUrlResponse = shortUrlController.shortUrlToLongUrl(shortToLongReq);
		Assert.isTrue(longUrl.equals(longUrlResponse.getResult()), "success");
	}

	@Test
	public void testInvalidShortUrlToLongUrl() {
		RequestEntity shortToLongReq = new RequestEntity();
		shortToLongReq.setShortUrl("");
		ResponseEntity response = shortUrlController.shortUrlToLongUrl(shortToLongReq);
		Assert.isTrue(response.getCode() == 500, "success");
	}

}
