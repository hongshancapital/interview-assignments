package com.example.tinyurl;

import com.example.tinyurl.dao.UrlDao;
import com.example.tinyurl.service.TinyUrlGenerator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tiny Url Controller mock tests
 * @author hermitriver
 */
@SpringBootTest
class TinyUrlControllerMockTests {
	private final static String TARGET_URL = "http://www.baidu.com";
	private final static String TINY_URL = "abed";
	protected MockMvc mockMvc;

	@InjectMocks
	private TinyUrlController controller;

	@MockBean
	TinyUrlGenerator tinyUrlGenerator;

	@MockBean
	UrlDao urlDao;

	/** Setup for each test */
	@BeforeEach()
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	/** Test generate method in TinyUrlController */
	@Test
	void testGenerate() throws Exception {
		controller.tinyUrlGenerator = this.tinyUrlGenerator;
		controller.urlDao = this.urlDao;

		// Test empty record
		given(this.tinyUrlGenerator.generate(TARGET_URL)).willReturn(TINY_URL);
		given(this.urlDao.getMaxId()).willReturn(null);
		given(this.urlDao.save(TINY_URL, TARGET_URL)).willReturn(true);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/_generate?t=" + TARGET_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(TINY_URL));

		// Test failure to save database when generating tinyurl
		given(this.urlDao.save(TINY_URL, TARGET_URL)).willReturn(false);
		mockMvc.perform(MockMvcRequestBuilders
						.get("/_generate?t=" + TARGET_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
	}

	/** Test redirect method in TinyUrlController */
	@Test
	void testRedirect() throws Exception {
		controller.urlDao = this.urlDao;
		given(this.urlDao.getTargetUrl(TINY_URL)).willReturn(TARGET_URL);
		mockMvc.perform(MockMvcRequestBuilders
						.get("/" + TINY_URL)
						.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().is3xxRedirection())
				.andExpect(content().string(""));

		// Overlong tiny url
		mockMvc.perform(MockMvcRequestBuilders
						.get("/123456789")
						.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().string(TinyUrlController.INVALID_TINYURL_MESSAGE));

		// Invalid tiny url - punctuation character
		mockMvc.perform(MockMvcRequestBuilders
						.get("/!")
						.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().string(TinyUrlController.INVALID_TINYURL_MESSAGE));

		// Invalid tiny url - multinational character
		mockMvc.perform(MockMvcRequestBuilders
						.get("/中文")
						.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().string(TinyUrlController.INVALID_TINYURL_MESSAGE));

		// Can't find the target url
		given(this.urlDao.getTargetUrl(TINY_URL)).willReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
						.get("/" + TINY_URL)
						.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().string(TinyUrlController.INVALID_TINYURL_MESSAGE));
	}
}
