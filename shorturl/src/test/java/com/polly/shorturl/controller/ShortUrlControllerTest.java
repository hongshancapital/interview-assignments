package com.polly.shorturl.controller;

import com.polly.shorturl.service.IShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author polly
 * @date 2022.03.23 12:05:15
 */
@WebMvcTest
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ShortUrlController controller;
    @MockBean
    private IShortUrlService service;

    @Test
    public void test01() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/insert", "longurl")
                        .accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void test02() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/get", "longurl")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }
}
