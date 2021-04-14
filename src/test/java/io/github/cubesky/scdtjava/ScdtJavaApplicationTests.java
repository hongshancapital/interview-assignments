package io.github.cubesky.scdtjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cubesky.scdtjava.entity.LongUrlBean;
import io.github.cubesky.scdtjava.entity.ShortUrlBean;
import io.github.cubesky.scdtjava.entity.UrlBean;
import io.github.cubesky.scdtjava.service.StorageService;
import io.github.cubesky.scdtjava.service.UrlGenerateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class ScdtJavaApplicationTests {
	@Autowired
	private WebApplicationContext wac;

	@Resource
	private UrlGenerateService urlGenerateService;

	@Resource
	private StorageService storageService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void urlGenerate() {
		assert urlGenerateService.getShortUrl().equals("aaaaaaaa");
	}

	@Test
	void urlShorten() {
		assert storageService.longUrlToShortUrl("https://www.example.com").equals("aaaaaaaa");
	}

	@Test
	void urlShortenToLong() {
		storageService.longUrlToShortUrl("https://www.example.com");
		assert storageService.shortUrlToLongUrl("aaaaaaaa") != null;
		assert storageService.shortUrlToLongUrl("aaaaaaaa").equals("https://www.example.com");
		assert storageService.shortUrlToLongUrl("aaaaaaab") == null;
	}

	@Test
	public void urlRedirect() throws Exception {
		String urlHash = storageService.longUrlToShortUrl("https://www.example.com");
		mockMvc.perform(get("/s/" + urlHash)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("https://www.example.com"));
	}

	@Test
	public void urlRedirectEmpty() throws Exception {
		mockMvc.perform(get("/s/")).andExpect(status().isNotFound());
	}

	@Test
	public void urlRedirectNotFound() throws Exception {
		mockMvc.perform(get("/s/example")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/404.html"));
	}

	@Test
	void apiShorten() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		LongUrlBean bean = new LongUrlBean("https://www.example.com");
		UrlBean resultBean = new UrlBean("aaaaaaaa","https://www.example.com");
		mockMvc.perform(post("/api/toshorturl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(bean))
		).andExpect(status().isOk()).andExpect(content().string(mapper.writeValueAsString(resultBean)));
	}

	@Test
	void apiShortenToLong() throws Exception {
		String urlHash = storageService.longUrlToShortUrl("https://www.example.com");
		ObjectMapper mapper = new ObjectMapper();
		ShortUrlBean bean = new ShortUrlBean(urlHash);
		UrlBean resultBean = new UrlBean(urlHash,"https://www.example.com");
		mockMvc.perform(post("/api/tolongurl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(bean))
		).andExpect(status().isOk()).andExpect(
				content().json(mapper.writeValueAsString(resultBean)));
	}

	@Test
	void apiShortenToLongNotFound() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ShortUrlBean bean = new ShortUrlBean("aaaaaaaa");
		mockMvc.perform(post("/api/tolongurl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(bean))
		).andExpect(status().isNotFound());
	}

	@Test
	void apiShortenToLongEmpty() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		{
			ShortUrlBean bean = new ShortUrlBean("");
			mockMvc.perform(post("/api/tolongurl")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(bean))
			).andExpect(status().isBadRequest());
		}
		{
			ShortUrlBean bean = new ShortUrlBean(null);
			mockMvc.perform(post("/api/tolongurl")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(bean))
			).andExpect(status().isBadRequest());
		}
	}

	@AfterEach
	void afterTest(){
		urlGenerateService.reset();
		storageService.reset();
	}
}
