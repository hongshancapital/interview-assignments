package cn.sequoiacap.java.controller;

import cn.sequoiacap.java.common.def.Constants;
import cn.sequoiacap.java.common.result.CommonResult;
import cn.sequoiacap.java.common.utils.AutoIncreUtils;
import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShortUrlControllerTest extends TestCase {

	private MockMvc mockMvc;

	@InjectMocks
	ShortUrlController shortUrlController;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(shortUrlController).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void testPutLongUrl() throws Exception {
		String longUrl = "https://github.com/scdt-china/interview-assignments";
		MvcResult mr = this.mockMvc.perform(
				(
						post("/url/put")
								.contentType(MediaType.APPLICATION_JSON)
								.param("longUrl", longUrl)
				)
		).andExpect(status().isOk()).andReturn();
		CommonResult cr = JSON.parseObject(mr.getResponse().getContentAsString(), CommonResult.class);
		Assert.assertEquals(200, cr.getCode());
		String shortUrl = (String) cr.getData();
		MvcResult gmr = this.mockMvc.perform(
				(
						get("/url/get/" + shortUrl)
								.contentType(MediaType.APPLICATION_JSON)
				)
		).andExpect(status().isOk()).andReturn();
		CommonResult gcr = JSON.parseObject(gmr.getResponse().getContentAsString(), CommonResult.class);
		Assert.assertEquals(200, gcr.getCode());
		Assert.assertEquals(longUrl, gcr.getData());
	}

	@Test
	public void testPutLongUrlErr() throws Exception {
		for (int i = 0; i < Constants.URL_MAX_COUNT; i++) {
			AutoIncreUtils.getNextUrlId();
		}
		String longUrl = "https://github.com/scdt-china/interview-assignments";
		MvcResult mr = this.mockMvc.perform(
				(
						post("/url/put")
								.contentType(MediaType.APPLICATION_JSON)
								.param("longUrl", longUrl)
				)
		).andExpect(status().isOk()).andReturn();
		CommonResult cr = JSON.parseObject(mr.getResponse().getContentAsString(), CommonResult.class);
		Assert.assertEquals(500, cr.getCode());
	}

	@Test
	public void testGetLongUrl() throws Exception {
		String shortUrl = "zzzzzzzz";
		MvcResult gmr = this.mockMvc.perform(
				(
						get("/url/get/" + shortUrl)
								.contentType(MediaType.APPLICATION_JSON)
				)
		).andExpect(status().isOk()).andReturn();
		CommonResult gcr = JSON.parseObject(gmr.getResponse().getContentAsString(), CommonResult.class);
		Assert.assertEquals(404, gcr.getCode());
	}

}


