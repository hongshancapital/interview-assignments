package com.shycier.shorturl;

import com.shycier.shorturl.service.ShortUrlService;
import com.shycier.shorturl.service.impl.LruStore;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ShortUrlApplicationTests {

	public static final String ORIGIN_URL = "https://www.56jzt.com/doc/p2bt6xsy";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test01Save() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				                .get("/shortUrl/save")
				                .param("originUrl", ORIGIN_URL))
		       .andExpect(MockMvcResultMatchers.status().isOk())
		       .andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void test02Get() throws Exception {
		String shortUrl = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrl/save").param("originUrl", ORIGIN_URL))
		                          .andReturn().getResponse().getContentAsString();
		mockMvc.perform(MockMvcRequestBuilders
				                .get("/shortUrl/get")
				                .param("shortUrl", shortUrl))
		       .andExpect(MockMvcResultMatchers.status().isOk())
		       .andExpect(MockMvcResultMatchers.content().string(ORIGIN_URL))
		       .andDo(MockMvcResultHandlers.print());
	}

	@Autowired
	private LruStore<String, String> lruStore;

	@Test
	public void test03MaxSize() {
		lruStore.setMaxSize(10000);
		lruStore.setMaxMemory(-1L);
		lruStore.clear();
		for (int i = 0; i < 10010; i++) {
			lruStore.save(String.valueOf(i), ORIGIN_URL);
		}
		Assert.isTrue(lruStore.size() == 10000);
	}

	@Test
	public void test04MaxMemory() {
		lruStore.setMaxSize(-1);
		lruStore.setMaxMemory(1024*1024*100);
		lruStore.clear();
		for (int i = 0; i < 1000000; i++) {
			lruStore.save(String.valueOf(i), ORIGIN_URL);
		}
		Assert.isTrue(lruStore.size() < 1000000);
	}


}
