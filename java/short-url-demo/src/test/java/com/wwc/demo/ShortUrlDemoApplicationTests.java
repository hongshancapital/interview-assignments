package com.wwc.demo;

import com.wwc.demo.common.ResponseResult;
import com.wwc.demo.controller.ShortUrlController;
import com.wwc.demo.service.ShortUrlService;
import com.wwc.demo.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
class ShortUrlDemoApplicationTests {
	@Autowired
	private ShortUrlService shortUrlService;
	//启用web上下文
	@Autowired
	private ShortUrlController shortUrlController;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception{
		//使用上下文构建mockMvc
		mockMvc = MockMvcBuilders.standaloneSetup(shortUrlController).build();
	}

	@Test
	void contextLoads() {
	}

/*
	*/
/**
	 * 正常传值
	 *//*

	@Test
	public void getShortUrlController1() {

		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/s/getShortUrl")
							.accept(MediaType.APPLICATION_JSON)
							.param("longUrl", "https://www.cnblogs.com/fanhj147258/p/14694898.html"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn();
			log.info(mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
/**
	 * 传null
	 *//*

	@Test
	public void getShortUrlController2() {

		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/s/getShortUrl")
							.accept(MediaType.APPLICATION_JSON)
							.param("longUrl", null))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn();
			log.info(mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*/
/**
	 * 正常传值
	 *//*

	@Test
	public void getLongUrlController1() {

		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/s/{shortCode}}")
							.accept(MediaType.APPLICATION_JSON)
							.param("longUrl", "eLub3KL8"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn();
			log.info(mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*/
/**
	 * 传null
	 *//*

	@Test
	public void getLongUrlController2() {

		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/s/{shortCode}}")
							.accept(MediaType.APPLICATION_JSON)
							.param("longUrl", null))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print())
					.andReturn();
			log.info(mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/




	/**
	 * 测试长链接多次生成短连接的情况
	 */
	@Test
	void testTheSameLongUrl(){
		String longUrl="https://www.cnblogs.com/fanhj147258/p/14694898.html";
		try{
			ResponseResult result1=shortUrlService.getShortUrl(longUrl);
			log.info(">>> 第一次生成短连接返回：{}",result1.getData());
			ResponseResult result2=shortUrlService.getShortUrl(longUrl);
			log.info(">>> 第二次生成短连接返回：{}",result2.getData());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	void testSignLongUrl(){
		try{
			String longUrl="https://www.cnblogs.com/fanhj147258/p/14694898.html";
			ResponseResult result=shortUrlService.getShortUrl(longUrl);
/*			for(int i=0;i<10002;i++){
				ResponseResult result=shortUrlService.getShortUrl(longUrl+i);
				log.info(">>>低"+i+"次生成短连接返回：{}",result.getData());
			}*/

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	void test10000(){
		try{
			String longUrl="https://www.cnblogs.com/fanhj147258/p/14694898.html";
			for(int i=0;i<10002;i++){
				ResponseResult result=shortUrlService.getShortUrl(longUrl+i);
				log.info(">>>低"+i+"次生成短连接返回：{}",result.getData());
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}



	/**
	 * 测试不同长链接生成短连接的情况
	 */
	@Test
	void testDifferentLongUrl(){
		try{
			String longUrl1="https://www.cnblogs.com/fanhj147258/p/14694899.html";
			ResponseResult result1=shortUrlService.getShortUrl(longUrl1);
			log.info(">>> 长链接：{}，生成短连接：{}",longUrl1,result1.getData());
			String longUrl2="https://www.cnblogs.com/fanhj147258/p/14694898.html";
			ResponseResult result2=shortUrlService.getShortUrl(longUrl2);
			log.info(">>> 长链接：{}，生成短连接：{}",longUrl2,result2.getData());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 测试不同短码获取长链接
	 */
	@Test
	void testDifferentShortCodeToLongUrl(){
		try{
			String shortCode1="eLub3KL8";
			ResponseResult result1=shortUrlService.getLongUrl(shortCode1);
			log.info(">>> 短码：{}，长连接：{}",shortCode1,result1.getData());
			String shortCode2="I44pVoKY";
			ResponseResult result2=shortUrlService.getLongUrl(shortCode2);
			log.info(">>> 短码：{}，长连接：{}",shortCode2,result2.getData());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 测试短码多次获取长链接
	 */
	@Test
	void testSameShortCodeToLongUrl(){
		try{
			String longUrl="https://www.cnblogs.com/fanhj147258/p/14694898.html";
			ResponseResult result=shortUrlService.getShortUrl(longUrl);
			String shortCode=result.getData().toString();
			shortCode=shortCode.substring(shortCode.length()-8,shortCode.length());
			ResponseResult result1=shortUrlService.getLongUrl(shortCode);
			log.info(">>> 短码：{}，长连接：{}",shortCode,result1.getData());
			ResponseResult result2=shortUrlService.getLongUrl(shortCode);
			log.info(">>> 短码：{}，长连接：{}",shortCode,result2.getData());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 测试不存在的短码获取长链接
	 */
	@Test
	void testNotExistsShortCodeToLongUrl(){
		try{
			String shortCode="ulTwDfxt";
			ResponseResult result=shortUrlService.getLongUrl(shortCode);
			log.info(">>> 短码：{}，长连接：{}",shortCode,result.getData());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 测试不传短码获取长链接
	 */
	@Test
	void testNullShortCodeToLongUrl(){
		try{
			ResponseResult result=shortUrlService.getLongUrl(null);
			log.info(">>> 短码：{}，长连接：{}",null,result.getData());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
