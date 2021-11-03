package net.iapploft.springboot;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortLinkControllerTest {

	@Autowired
	private MockMvc mvc;


	@Test
	public void TestNotCacheForLoadDB() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/link/org").param("shortLink","http://iapploft.net/01Lh5ykp").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result").value("https://iapploft.net/"));

	}

	@Test
	public void TestNotCacheAndNotDB() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/link/org").param("shortLink","http://iapploft.net/01Lh5yk1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("该连接码不存在！"));

	}


	@Test
	public void TestAutoScaleDB() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/link/org").param("shortLink","http://iapploft.net/03wj9CjC").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("该连接码不存在！"));

	}


	@Test
	public void TestGetShortLink() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/link/short").param("orgLink","https://play.google.com/store/apps/details?id=jp.gungho.pad").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result").value("http://iapploft.net/01L9zO9P"));


	}


	@Test
	public void TestGetShortLinkAgent() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/link/short").param("orgLink","https://play.google.com/store/apps/details?id=jp.gungho.pad").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result").value("http://iapploft.net/01L9zO9P"));

	}



}
