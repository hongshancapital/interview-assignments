package scdt.interview.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import scdt.interview.java.UrlShortenerApplication;
import scdt.interview.java.api.ShortenController;
import scdt.interview.java.common.constant.ShortenConstant;
import scdt.interview.java.common.entity.ShortenRequest;
import scdt.interview.java.service.SequenceService;
import scdt.interview.java.service.ShortenService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShortenerApplication.class)
public class UrlShortenerApplicationTest {

	@Resource
	private ShortenService shortenService;

	@Resource
	private SequenceService sequenceService;

	@Resource
	private ShortenController shortenController;

	@Test
	public void sequenceTest() {
		long firstSeq = sequenceService.getNextSeq();
		long secondSeq = sequenceService.getNextSeq();
		Assert.assertTrue("步长测试", secondSeq - firstSeq == 1);
	}
	
	@Test
	public void testApiGenerateShort() {
		ShortenRequest shortenRequest = new ShortenRequest();
		shortenRequest.setLongUrl("https://host.com/123/456/789/10?param1=p1_value&param2=p2_value");
		String shorten = shortenController.generateShort(shortenRequest);
		Assert.assertEquals("短链接", ShortenConstant.SHORT_DOMAIN_HOST + "0", shorten);
	}

	@Test
	public void testApiGetLong() {
		String url = "https://host.com/123/456/789/10?param1=p1_value&param2=p2_value";
		String longUrl = shortenController.getLong("0");
		Assert.assertEquals("长域名读取", url, longUrl);
	}
	
	@Test
	public void generateTest() {
		String url = "https://host.com/123/456/789/10?param1=p1_value&param2=p2_value";
		String shorten = shortenService.generate(url);
		Assert.assertTrue("长度满足要求", shorten.length() <= 8);
		String getLongUrl = shortenService.getLongURL(shorten);
		Assert.assertEquals("获取长域名成功", url, getLongUrl);
	}

	@Test
	public void generateFailureTest() throws MalformedURLException {
		String url = "hsdttps://host.com/123/456/789/10?param1=p1_value&param2=p2_value";
		try{
			String shorten = shortenService.generate(url.toString());
		}catch(Exception e) {
			Assert.assertEquals("长域名不合法", "不合法", e.getMessage());
		}
	}
}
