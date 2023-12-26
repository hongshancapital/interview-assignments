package scdt.interview.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import scdt.interview.java.UrlShortenerApplication;
import scdt.interview.java.api.ShortenController;
import scdt.interview.java.common.entity.ShortenRequest;
import scdt.interview.java.common.utils.LRUMapCache;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShortenerApplication.class)
public class UrlShortenerPerformenceTest {

	@Resource
	private ShortenController shortenController;
	
	@Resource
	private LRUMapCache<String, String> shortCacheMap;
	
	/**
	 * 并发测试
	 * 50，5000000
	 */
	@Test
	public void testApiGenerateShort() {
		int poolSize = Runtime.getRuntime().availableProcessors() * 2;
		System.out.println(poolSize);
		ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
		long start = System.currentTimeMillis();
		for(int i=0;i<500000;i++) {
			executorService.execute(new generateThread(i, shortenController));
		}
		while(((ThreadPoolExecutor)executorService).getActiveCount() > 0){
			try {
				System.out.println(shortCacheMap.size());
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long resTime = System.currentTimeMillis() - start;
		Assert.assertTrue(resTime < 10000);
	}
}

class generateThread extends Thread{
	private int index;
	private ShortenController shortenController;

	public generateThread(int index, ShortenController shortenController) {
		this.index = index;
		this.shortenController = shortenController;
	}
	
	@Override
	public void run() {
		ShortenRequest shortenRequest = new ShortenRequest();
		shortenRequest.setLongUrl("https://host.com/123/456/789/" + index + "?param1=p1_value&param2=p2_value");
		shortenController.generateShort(shortenRequest);
	}
}