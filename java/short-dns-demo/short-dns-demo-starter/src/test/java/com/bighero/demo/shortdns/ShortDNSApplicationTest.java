 package com.bighero.demo.shortdns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bighero.demo.shortdns.controller.ShortDNSController;
import com.bighero.demo.shortdns.domain.entity.LongDN;
import com.bighero.demo.shortdns.domain.service.ITransformShortDN;
import com.bighero.demo.shortdns.url.app.service.DNAppService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bighero
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes=ShortDNSApplication.class)
@AutoConfigureMockMvc
public class ShortDNSApplicationTest {
	   @Autowired
	   private MockMvc mockMvc;
	   @Autowired
	   @Qualifier("byID")
	   private ITransformShortDN transformShortDN;
	
	@Test
	public void test_saveshorturl() throws Exception  {
		log.info("test_saveshorturl start!");
		String reqjson="{\r\n" + 
				"  \"data\": {\r\n" + 
				"    \"longDN\": \"https://www.baidu.com/gggg\"\r\n" + 
				"  }\r\n" + 
				"}";
		try {
			MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/shortdns/url")
	                .content(reqjson)
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .accept(MediaType.APPLICATION_JSON);
	                mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
		log.info("test_saveshorturl end!");
	}
	
	@Test
	public void test_saveshorturl_error() throws Exception  {
		log.info("test_saveshorturl start!");
		String reqjson="{\r\n" + 
				"  \"data\": {\r\n" + 
				"    \"longDN\": \"https//www.baidu.com/gggg\"\r\n" + 
				"  }\r\n" + 
				"}";
		try {
			MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/shortdns/url")
	                .content(reqjson)
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .accept(MediaType.APPLICATION_JSON);
	                mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
		log.info("test_saveshorturl end!");
	}
	
	@Test
	public void test_saveshorturl_long_error() throws Exception  {
		log.info("test_saveshorturl start!");
		String reqjson="{\r\n" + 
				"  \"data\": {\r\n" + 
				"    \"longDN\": \"https//www.baidu.com/ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff\"\r\n" + 
				"  }\r\n" + 
				"}";
		try {
			MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/shortdns/url")
	                .content(reqjson)
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .accept(MediaType.APPLICATION_JSON);
	                mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
		log.info("test_saveshorturl end!");
	}
	
	@Test
	public void test_saveshorturl_null_error() throws Exception  {
		log.info("test_saveshorturl start!");
		String reqjson="{\r\n" + 
				"  \"data\": {\r\n" + 
				"    \"longDN\": \"\"\r\n" + 
				"  }\r\n" + 
				"}";
		try {
			MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/shortdns/url")
	                .content(reqjson)
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .accept(MediaType.APPLICATION_JSON);
	                mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
		log.info("test_saveshorturl end!");
	}
	
	@Test
	public void test_getshortdn() throws Exception  {
		log.info("test_getshortdn start!");
		 
		try {
			MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.get("/shortdns/url/E3yEnu")
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .accept(MediaType.APPLICATION_JSON);
	                mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
		log.info("test_getshortdn end!");
	}
	
	
	
	@Test
	public void test_getshortdn_error() throws Exception  {
		log.info("test_getshortdn start!");
		 
		try {
			MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.get("/shortdns/url/afadfafadsf")
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .accept(MediaType.APPLICATION_JSON);
	                mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
		log.info("test_getshortdn end!");
	}
	
	@Test
	public void test_getshortdn_null_error() throws Exception  {
		log.info("test_getshortdn start!");
		 
		try {
			MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.get("/shortdns/url/null")
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .accept(MediaType.APPLICATION_JSON);
	                mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
		log.info("test_getshortdn end!");
	}
	
	@Test
	public void test_transform() {
		transformShortDN.transform(new LongDN("http://www.baidu.com/t/dr/de"));
	}
}
