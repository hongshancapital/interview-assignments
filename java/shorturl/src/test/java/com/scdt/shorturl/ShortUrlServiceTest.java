package com.scdt.shorturl;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSON;
import com.scdt.shorturl.config.SnowflakeConfig;
import com.scdt.shorturl.service.ShortUrlService;
import com.scdt.shorturl.util.NumericConvertUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ShortUrlApplication.class})
public class ShortUrlServiceTest{
	@Autowired private ShortUrlService shortUrlService;
	@Autowired private SnowflakeConfig snowflakeConfig;
	
	private static String getRandomOfLengthRange(int minLength,int maxLength){
		Random random=new Random();
		int length=random.nextInt(maxLength-minLength+1)+minLength;//取minLen和maxLen之间的随机数
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<length;i++)sb.append(NumericConvertUtil.digits[random.nextInt(NumericConvertUtil.digits.length)]);
		return sb.toString();
	}
	
	/**
	 * 工具方法，测试长地址转地址，随机产生100个长地址，将其转为短地址，进行验重，并存入本地变量long2shortMap，验证同一长地址前后两次转换短地址是否一致。
	 * 之所以存在serviceId和workerId两个参数，是为了实现通用，满足单实例情况的默认值和多实例情况的自定义值，参数如果为空则表示使用shortUrlService对象属性自带的发号器，否则通过参数获取对应的发号器。
	 * @author 周小建
	 * @param workerId
	 * @param serviceId
	 */
	private void testLong2Short(Long workerId,Long serviceId){
		Map<String,String>tempLong2shortMap=new LinkedHashMap<String,String>();
		for(int i=0;i<100;i++){
			String originalUrl=getRandomOfLengthRange(10,20);
			String shortUrl=shortUrlService.long2short(originalUrl,workerId,serviceId);
			if(shortUrl.startsWith("existsError:")){		
				String existsOriginalUrl=shortUrl.replaceFirst("existsError:","");
				Assert.assertTrue(originalUrl+":"+shortUrl+"存在重复，对应长地址："+existsOriginalUrl,false);
			}
			String secondShortUrl=shortUrlService.long2short(originalUrl,workerId,serviceId);
			Assert.assertEquals("长地址"+originalUrl+"两次转换短地址不一致："+shortUrl+","+secondShortUrl,shortUrl,secondShortUrl);
			tempLong2shortMap.put(originalUrl,shortUrl);
		}
		System.out.println("\tworkerId"+workerId+"对应数据：\r\n\t\t"+JSON.toJSONString(tempLong2shortMap));
	}
	
	/**
	 * 测试长地址转地址，测试长地址转地址，随机产生100个长地址，将其转为短地址，进行验重，并存入本地变量long2shortMap，最后验证同一长地址前后两次转换短地址是否一致。
	 * 使用shortUrlService对象属性自带的发号器。
	 * @author 周小建
	 */
	@Test public void testLong2Short(){
		testLong2Short(null,null);
	}
	
	/**
	 * 测试长地址转地址，workerId超出阈值。
	 * 通过参数获取对应的发号器但获取失败。
	 * @author 周小建
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLong2ShortThanWorkerId(){
		testLong2Short((-1L^(-1L<<snowflakeConfig.getWorkerIdBits()))+1,-1L^(-1L<<snowflakeConfig.getServiceIdBits()));
		
	}
	
	/**
	 * 测试长地址转地址，workerId超出阈值。
	 * 通过参数获取对应的发号器但获取失败。
	 * @author 周小建
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLong2ShortLessWorkerId(){
		testLong2Short(-1L,-1L^(-1L<<snowflakeConfig.getServiceIdBits()));
		
	}
	
	/**
	 * 测试长地址转地址，serviceId超出阈值。
	 * 通过参数获取对应的发号器但获取失败。
	 * @author 周小建
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLong2ShortThanServiceId(){
		testLong2Short(-1L^(-1L<<snowflakeConfig.getWorkerIdBits()),(-1L^(-1L<<snowflakeConfig.getServiceIdBits()))+1);
	}
	
	/**
	 * 测试长地址转地址，serviceId超出阈值。
	 * 通过参数获取对应的发号器但获取失败。
	 * @author 周小建
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLong2ShortLessServiceId(){
		testLong2Short(-1L^(-1L<<snowflakeConfig.getWorkerIdBits()),-1L);
	}
	
	/**
	 * 测试短地址转长地址，将本地变量long2shortMap的短地址取出，逐个转为长地址，与已存长地址一一比对。
	 * @author 周小建
	 */
	@Test public void testShort2Long(){
		for(Iterator<Map.Entry<Long,String>>iterator=ShortUrlService.short2longMap.entrySet().iterator();iterator.hasNext();){
			Map.Entry<Long,String>entry=iterator.next();
			Long serialNumeric=entry.getKey();
			String shortUrl=NumericConvertUtil.toOtherNumber(serialNumeric);
			String originalUrl=entry.getValue();
			String realOrignalUrl=shortUrlService.short2long(shortUrl);
			Assert.assertEquals(shortUrl+":"+originalUrl+"!="+realOrignalUrl,originalUrl,realOrignalUrl);
		}
	}
	
	/**
	 * 整合测试短地址和长地址，随机产生100个短地址，将其转为长地址，进行验重，并存入本地变量long2shortMap，验证同一长地址前后两次转换短地址是否一致，再将其取出反向转为长地址进行比对。
	 * 使用shortUrlService对象属性自带的发号器。
	 * @author 周小建
	 */
	@Test public void testTogether(){
		testLong2Short(null,null);
		testShort2Long();
	}
	
	/**
	 * 测试并发，模拟分布式环境多个实例并发执行。
	 * @author 周小建
	 */
	@Test public void testConcurrent(){
		int count=4;
		final CountDownLatch countDownLatch=new CountDownLatch(count);
		for(int i=0;i<count;i++){
			final Long workerId=new Long(i);
			new Thread(new Runnable(){
				@Override
				public void run(){
					System.out.println("workerId"+workerId+"开始运行");
					testLong2Short(0L,workerId);
					System.out.println("workerId"+workerId+"结束运行");
					countDownLatch.countDown();
				}
			}).start();
		}
		try{
			countDownLatch.await();
			testShort2Long();
			System.out.println("\t最终数据：\r\n\t\t"+JSON.toJSONString(ShortUrlService.long2shortMap));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试批量并发，模拟分布式环境多个实例并发执行，并且循环执行多次，尽可能模拟实际的高并发环境。
	 * @author 周小建
	 */
	@Test public void testMultiConcurrent(){
		String separator="==================================";
		for(int i=0;i<2;i++){
			if(i>0){
				try{
					Thread.sleep(1000L*5);//主线程休眠5秒
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				System.out.println("\r\n\r\n\n");
			}
			System.out.println(separator+"\r\n第"+i+"轮开始");
			testConcurrent();
			System.out.println("第"+i+"轮结束\r\n"+separator);
		}
	}
}