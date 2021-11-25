package com.scdt.interview.url;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UrlControllerTests {

    private String validUrl = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=%E7%BA%A2%E6%9D%89%E8%B5%84%E6%9C%AC&oq=%25E7%259F%25AD%25E7%25BD%2591%25E5%259D%2580%25E7%2594%259F%25E6%2588%2590%25E7%25AE%2597%25E6%25B3%2595&rsv_pq=9077fa2f00015d27&rsv_t=2ad8Qao6agxjVUy53Ko1CxFL8DnUNOf92%2Bvguo0VOUCmKlKYU5XFE4IYOjM&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=9&rsv_sug1=15&rsv_sug7=100&bs=%E7%9F%AD%E7%BD%91%E5%9D%80%E7%94%9F%E6%88%90%E7%AE%97%E6%B3%95";

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }



    @Test
    void storeLongUrlWithValidLongUrl() throws Exception {

        mockMvc.perform(post("/url/long").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("longUrl", validUrl)).andExpect(status().isOk()).andExpect(content().string(CoreMatchers.containsString("http://dwz.com/0")));
    }

    @Test
    void storeLongUrlWithInvalidLongUrl() throws Exception {

        mockMvc.perform(post("/url/long").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("longUrl", "dfsdfasdfasdf")).andExpect(status().is4xxClientError());
        mockMvc.perform(post("/url/long").contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().is4xxClientError());
    }


    @Test
    void getLongUrlWithValidShortUrl() throws Exception {

        mockMvc.perform(get("/url/long").param("shortUrl", "http://dwz.com/0")).andExpect(status().isOk()).andExpect(content().string(CoreMatchers.containsString(validUrl)));
    }

    @Test
    void notGetLongUrlWithValidShortUrl() throws Exception {

        mockMvc.perform(get("/url/long").param("shortUrl", "http://dwz.com/notfound")).andExpect(status().isOk()).andExpect(content().json("{'code':'fail', 'message': '长链接不存在', 'data':null}"));
    }

    @Test
    void getLongUrlWithInvalidShortUrl() throws Exception {

        mockMvc.perform(get("/url/long").param("shortUrl", "dfsdfasdfasdf")).andExpect(status().is4xxClientError());
        mockMvc.perform(get("/url/long")).andExpect(status().is4xxClientError());
    }




}
