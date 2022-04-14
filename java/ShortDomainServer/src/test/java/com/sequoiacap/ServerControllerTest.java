package com.sequoiacap;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

import com.sequoiacap.model.UrlInfo;
import com.sequoiacap.util.NumericConvertUtil;

/**
 * 
 * @author zoubin
 */
public class ServerControllerTest {
	ServerController c = new ServerController();
	int size = Runtime.getRuntime().availableProcessors() * 2;
	ExecutorService pool = Executors.newFixedThreadPool(size); 
	
	@Test
	public void testStoreAndGet() {
		System.setProperty("serverCluster", "127.0.0.1:8181,127.0.0.1:8182");
		try {
			new ServerController().buildSeed();
		} catch (Exception e1) {
			Assert.assertEquals(e1.getMessage(), "currentServerAddr can not match any configed server");
		}
		c.getSeed().set(Long.MAX_VALUE);
		try {
			c.store("https://github.com/scdt-usa/xxxx");
		} catch (Exception e1) {
			Assert.assertEquals(e1.getMessage(), "java.lang.IllegalArgumentException: ShortUrl is too long");
		}
		c.getSeed().set(0);
		Assert.assertEquals(new UrlInfo(400, null, "短域名不能为空"), c.get(null));
		Assert.assertEquals(new UrlInfo(400, null, "长域名不能为空"), c.store(null));
		Assert.assertEquals(new UrlInfo(404, null, "没找到相应的短域名"), c.get("11sa1qq1"));
		Assert.assertEquals(new UrlInfo(200, "0", ""),
				c.store("https://github.com/scdt-china/interview-assignments/tree/master/java"));
		Assert.assertEquals(new UrlInfo(200, "0", ""),
				c.store("https://github.com/scdt-china/interview-assignments/tree/master/java"));
		Assert.assertEquals(new UrlInfo(200, "1", ""), c.store("https://github.com/scdt-china/x1"));
		Assert.assertEquals(new UrlInfo(200, "2", ""), c.store("https://github.com/scdt-china/x4"));
		Assert.assertEquals(new UrlInfo(200, "3", ""), c.store("https://github.com/scdt-china/x7"));
		Assert.assertEquals(new UrlInfo(200, "4", ""), c.store("https://github.com/scdt-china/x10"));
		Assert.assertEquals(new UrlInfo(200, "5", ""), c.store("https://github.com/scdt-china/x22"));
		String shortUrl = "5";
		int k = 22;
		for (int i = 0; i < 1000000; i++) {
			long x = NumericConvertUtil.toDecimalNumber(shortUrl, 62);
			x++;
			shortUrl = NumericConvertUtil.toOtherNumberSystem(x, 62);
			Assert.assertEquals(new UrlInfo(200, shortUrl , ""), c.store("https://github.com/scdt-china/y" + NumericConvertUtil.toOtherNumberSystem(k++, 62)));
			System.out.println("x:"+ x +"s:"+ shortUrl);
		}
		Assert.assertEquals(new UrlInfo(200, "https://github.com/scdt-china/interview-assignments/tree/master/java", ""), c.get("0"));
		Assert.assertNotEquals(new UrlInfo(200, "0", ""),
				c.store("https://github.com/scdt-china/interview-assignments/tree/master/java"));
		final CountDownLatch latch = new CountDownLatch(size);
		for(int i = 0; i < size; ) {
			pool.execute(new ExecTask("https://github.com/scdt-china/qex" + (i++), latch));
		}
		try {
			latch.await();
			Assert.assertEquals(new UrlInfo(200, "https://github.com/scdt-china/x7", ""), c.get("3"));
			Assert.assertNotEquals(new UrlInfo(200, "3", ""), c.store("https://github.com/scdt-china/x7"));
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class ExecTask implements Runnable {
		final String baseUrl;
		final CountDownLatch latch;
		public ExecTask(String baseUrl, final CountDownLatch latch) {
			this.baseUrl = baseUrl;
			this.latch = latch;
		}
		@Override
		public void run() {
			try {
				for (int i = 0; i < 100000; i++) {
					Assert.assertNotNull(c.store(new StringBuilder(baseUrl)
							.append(NumericConvertUtil.toOtherNumberSystem(i++, 62)).toString()));
				 System.out.println(Thread.currentThread().getId()+":"+i);
				} 
			} finally {
				latch.countDown();
			}
		}
	}
}
