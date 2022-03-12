package com.sequoia.interview.shorturl;

import com.sequoia.interview.shorturl.service.ShortUrlService;
import com.sequoia.interview.shorturl.service.impl.ShortUrlServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class ShorturlApplicationTests {
//@ContextConfiguration
//@WebAppConfiguration
	@Value("${app.start}")
	private String start;

	@Value("${app.end}")
	private String end;

	@Autowired
	private ShortUrlService shortUrlServiceImpl;

	@Autowired
	private StringRedisTemplate redis;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		redis.opsForValue().set("curValue",start);
		delAll4RedisHash("url");
		delAll4RedisHash("shortUrl");

	}

	private void initRedis(){
		redis.opsForValue().set("curValue",start);
		delAll4RedisHash("url");
		delAll4RedisHash("shortUrl");
	}

	private void delAll4RedisHash(String key){
		Set keys= redis.opsForHash().keys(key);
		for (Iterator it = keys.iterator(); it.hasNext(); ) {
			String field = (String)it.next();
			redis.opsForHash().delete("url",field);
		}
	}

//	@Test
//	void contextLoads() {
//
//	}

	@Test
	public void testInit(){
		initRedis();
		//测试从配置文件中读取curValue
		redis.delete("curValue");
		shortUrlServiceImpl.setCurValue(null);
		Assert.assertEquals("AAAA0001", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/1"));
		//测试从ｒｅｄｉｓ中读取curValue
		redis.opsForValue().set("curValue","AAAA000a");
		shortUrlServiceImpl.setCurValue(null);
		Assert.assertEquals("AAAA000b", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/2"));

	}

	@Test
	public void testGenShortUrl() {
		initRedis();
		// 分段结束值
		shortUrlServiceImpl.setCurValue(this.end);
		Assert.assertEquals(start, shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/1"));
		// 开始值
		shortUrlServiceImpl.setCurValue(this.start);
		Assert.assertEquals("AAAA0001", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/2"));
		// 已存在映射关系
		Assert.assertEquals("AAAA0001", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/2"));
		// 9-A 边界
		shortUrlServiceImpl.setCurValue("AAAA0009");
		Assert.assertEquals("AAAA000A", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/3"));
		// Z-a 边界
		shortUrlServiceImpl.setCurValue("AAAA000Z");
		Assert.assertEquals("AAAA000a", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/4"));
		// z-0 边界
		shortUrlServiceImpl.setCurValue("AAAA000z");
		Assert.assertEquals("AAAA0010", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/5"));
		// 进位9-Z
		shortUrlServiceImpl.setCurValue("AAAA009z");
		Assert.assertEquals("AAAA00A0", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/6"));
		// 进位Z-a
		shortUrlServiceImpl.setCurValue("AAAA00Zz");
		Assert.assertEquals("AAAA00a0", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/7"));
		// 普通值
		shortUrlServiceImpl.setCurValue("AAAA00Zb");
		Assert.assertEquals("AAAA00Zc", shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/8"));

	}

	@Test
	public void testPlusOne(){
		char[] curValue="AAA00000".toCharArray();
		for (int i = 0; i <100000; i++) {
			curValue=shortUrlServiceImpl.plusOne(curValue);
		}
		//２00000000　2亿　AAAAAA8S　　100000000１亿：AAA6laZE  10万　AAA00Q0u
		Assert.assertEquals("AAA00Q0u", String.valueOf(curValue));
	}

	@Test
	public void testRestfulGen() throws Exception{
		initRedis();
		shortUrlServiceImpl.setCurValue(start);

		HashMap<String,String> params=new HashMap<>(1);
		//params.put("url","http://js.ixingk.org/1");
		for(int i=1;i<9;i++){
			params.clear();
			params.put("url","http://js.ixingk.org/"+String.valueOf(i));
			Assert.assertEquals("AAAA000"+i,doGet("/api/shorturl/gen",params));

		}
	}

	@Test
	public void testRestfulGenNTimes(){
		initRedis();
		delAll4RedisHash("url");
		delAll4RedisHash("shortUrl");
		HashMap<String,String> params=new HashMap<>(1);
		long times=100;
		MockHttpServletRequestBuilder request;
		long start = System.currentTimeMillis();
		//params.put("url","http://js.ixingk.org/1");
		for(int i=1;i<times;i++){
//			params.clear();
//			params.put("url","http://js.ixingk.org/"+String.valueOf(i));
			try {
				//doGet("/api/shorturl/gen", params);
				request= get("/api/shorturl/gen")
						.param("url","http://js.ixingk.org/"+String.valueOf(i));
				mvc.perform(request).andExpect(status().isOk());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Assert.assertEquals("AAAAAA0"+i,doGet("/api/shorturl/gen",params));
		}

		long end = System.currentTimeMillis();
		long len = redis.opsForHash().size("shortUrl");
		System.out.println("执行次数："+times+ ", 执行时间(ms): "+(end-start)+ " 共生成："+len+ "　个短网址");

	}

	@Test
	public void testRestfulGetUrlNTimes(){
		//initRedis();
		//testRestfulGenNTimes();
		HashMap<String,String> params=new HashMap<>(1);
		long times=100;
		MockHttpServletRequestBuilder request;
		long start = System.currentTimeMillis();
		//params.put("url","http://js.ixingk.org/1");
		Set keys= redis.opsForHash().keys("shortUrl");
		int len = keys.size();//redis.opsForHash()..size("shortUrl");
		for (Iterator it = keys.iterator(); it.hasNext(); ) {
			String shortUrl = (String) it.next();
			try {
				//doGet("/api/shorturl/gen", params);
				request= get("/api/shorturl/geturl")
						.param("s",shortUrl);
				mvc.perform(request).andExpect(status().isOk());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("执行次数："+times+ ", 执行时间(ms): "+(end-start)+ " 共查询："+len+ " 次");

	}

	@Test
	public void testRestfulGetUrl() throws Exception{
		//准备数据
		initRedis();
		shortUrlServiceImpl.setCurValue(start);
		for(int i=1;i<9;i++){
			shortUrlServiceImpl.genShortUrl("http://js.ixingk.org/"+i);
		}

		HashMap<String,String> params=new HashMap<>(1);
		for(int i=1;i<9;i++){
			params.clear();
			params.put("s","AAAA000"+String.valueOf(i));
			Assert.assertEquals("http://js.ixingk.org/"+i, doGet("/api/shorturl/geturl",params));

		}
	}

	private String doGet(String url, HashMap<String,String> params) throws Exception{
		MvcResult result = null;
		MockHttpServletRequestBuilder request=  MockMvcRequestBuilders.get(url);
				//.contentType(MediaType.APPLICATION_JSON);
		for (String key:params.keySet()) {
			request.param(key,params.get(key));
		}
		result = mvc.perform(request).andExpect(status().isOk()).andReturn();
		String body= result.getResponse().getContentAsString();
		return body;
	}
}
