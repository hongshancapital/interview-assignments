package com.interview.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 
 * @类名称   com.interview.test.ShortUrlApiTest.java
 * @类描述   <pre>api层测试类，进行api层测试、集成测试</pre>
 * @作者     
 * @创建时间 2021年3月21日下午8:48:04
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	 zhangsheng 	2021年3月21日下午8:48:04             
 *     ------------------------------------------------------
 * </pre>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShortUrlApiTest {
	
	final String SERVER_URL="http://127.0.0.1";
	
	@Autowired 
    private MockMvc mockMvc;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@LocalServerPort
    private int port;
	
	@Test
	public void testStoreShortUrl() throws Exception {
		 String json="{\"longUrl\": \"www.baidu.com\"}";
		 mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/store")
	                    .accept(MediaType.APPLICATION_JSON)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(json.getBytes()) //传json参数
	            )
	           .andExpect(MockMvcResultMatchers.status().isOk())
	           .andExpect(MockMvcResultMatchers.jsonPath("$.data.shortUrl").value("http://00000002"))
	           .andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testQueryLongUrlByShortUrl() throws Exception {
		String json="{\"shortUrl\": \"http://00000002\"}";
		 mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/find")
	                    .accept(MediaType.APPLICATION_JSON)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(json.getBytes()) //传json参数
	            )
	           .andExpect(MockMvcResultMatchers.status().isOk())
	           .andExpect(MockMvcResultMatchers.jsonPath("$.data.longUrl").value("www.baidu.com"))
	           .andDo(MockMvcResultHandlers.print());
	}
	
	/**
	 * 
	 * @方法名称 testSitCase
	 * @功能描述 <pre>集成测试，先发请求转换长域名并存储，再发请求根据短域名转换为原来的长域名</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午8:46:34
	 * @param url
	 * @throws Exception
	 * @return
	 */
	@ParameterizedTest
	@ValueSource(strings = {"www.baidu.com", "www.baidu.com","www.sohu.com"})
	public void testSitCase(String url) throws Exception {
		//先发请求转换长域名并存储
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("longUrl", url);
		Map<String, Object> responseMap = this.restTemplate.postForObject(SERVER_URL + ":" + port +"/shorturl/store", requestMap, Map.class);
		Map<String, Object> dataMap = (Map<String, Object>)responseMap.get("data");
		//获取返回的短域名
		String shortUrl = (String)dataMap.get("shortUrl");
		System.out.println("shortUrl:"+shortUrl);
		
		requestMap.clear();
		responseMap.clear();
		dataMap.clear();
		
		requestMap.put("shortUrl", shortUrl);
		responseMap = this.restTemplate.postForObject(SERVER_URL + ":" + port+"/shorturl/find", requestMap, Map.class);
		dataMap = (Map<String, Object>)responseMap.get("data");
		//获取返回的长域名
		String longUrl = (String)dataMap.get("longUrl");
		System.out.println("longUrl:"+longUrl);
		//验证是否与最初的url一致
		Assertions.assertTrue(url.equals(longUrl));
		
	}
}
