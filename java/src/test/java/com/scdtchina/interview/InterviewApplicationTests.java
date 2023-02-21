package com.scdtchina.interview;

import com.scdtchina.interview.controller.DomainConverterController;
import com.scdtchina.interview.dto.req.GetLongUrlReq;
import com.scdtchina.interview.dto.req.GetShortUrlReq;
import com.scdtchina.interview.dto.rsp.BaseRsp;
import com.scdtchina.interview.exception.BusinessException;
import com.scdtchina.interview.service.DomainRelationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = {DomainConverterController.class, DomainRelationService.class})
class InterviewApplicationTests {

	@Autowired
	private DomainRelationService domainRelationService;

	@Autowired
	private DomainConverterController domainConverterController;

	@Test
	public void testGetShortUrlService() {
		String longUrl = "http://www.baidu.com";
		Assert.assertNotNull(domainRelationService.shortenUrl(longUrl));
		Assert.assertNotNull(domainRelationService.shortenUrl(longUrl));
		Assert.assertNull(domainRelationService.shortenUrl(""));
	}

	@Test
	public void testGetLongUrlService() {
		String rawUrl = "http://www.baidu.com";
		String shortUrl = domainRelationService.shortenUrl(rawUrl);
		String longUrl = domainRelationService.getLongUrl(shortUrl);
		Assert.assertEquals(longUrl, rawUrl);
		Assert.assertNull(domainRelationService.getLongUrl(null));
	}

	@Test
	public void testGetNotExistLongUrlService() {
		try {
			domainRelationService.getLongUrl("notExist");
		} catch (BusinessException e) {
			Assert.assertEquals(e.getErrorCode(), 1001001);
		}
	}

	@Test
	public void testGetShortUrlController() {
		GetShortUrlReq req = new GetShortUrlReq();
		req.setUrl("http://www.baidu.com");
		ResponseEntity<BaseRsp> responseEntity = domainConverterController.getShortUrl(req);
		Assert.assertNotNull(responseEntity);
		Assert.assertEquals(responseEntity.getStatusCode().value(), 200);
		String shortUrl = (String) responseEntity.getBody().getData();
		Assert.assertEquals(shortUrl, "49ImVQ");
	}

	@Test
	public void testGetLongUrlController() {
		String longUrl = "http://www.baidu.com";
		GetShortUrlReq getShortUrlReq = new GetShortUrlReq();
		getShortUrlReq.setUrl(longUrl);
		ResponseEntity<BaseRsp> responseEntity = domainConverterController.getShortUrl(getShortUrlReq);
		Assert.assertEquals(responseEntity.getStatusCode().value(), 200);

		GetLongUrlReq getLongUrlReq = new GetLongUrlReq();
		getLongUrlReq.setUrl("49ImVQ");
		ResponseEntity<BaseRsp> resp = domainConverterController.getLongUrl(getLongUrlReq);
		Assert.assertEquals(resp.getStatusCode().value(), 200);
		Assert.assertEquals(resp.getBody().getData(), longUrl);
	}


}
