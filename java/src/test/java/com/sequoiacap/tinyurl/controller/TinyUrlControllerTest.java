package com.sequoiacap.tinyurl.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TinyUrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_create_tinyurl_is_ok() throws Exception {
        MvcResult result = mockMvc.perform(post("/tinyurl").content("http://www.123.com"))
                .andExpect(status().isOk()).andReturn();
        assertEquals("1FpkpZ1q", result.getResponse().getContentAsString());
    }

    @Test
    void should_create_tinyurl_is_bad_request() throws Exception {
        mockMvc.perform(post("/tinyurl").content("www.123.com")).andExpect(status().isBadRequest());
    }

    @Test
    void should_get_url_is_ok() throws Exception {
        mockMvc.perform(post("/tinyurl").content("http://www.123.com")).andExpect(status().isOk());
        MvcResult result = mockMvc.perform(get("/tinyurl/1FpkpZ1q"))
                .andExpect(status().isOk()).andReturn();
        assertEquals("http://www.123.com", result.getResponse().getContentAsString());
    }


    @Test
    void should_get_url_is_not_found() throws Exception {
        MvcResult result = mockMvc.perform(get("/tinyurl/1FpkpZ1q"))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void should_get_url_is_bad_request() throws Exception {
        mockMvc.perform(get("/tinyurl/1FpkpZ1")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/tinyurl/&*123456")).andExpect(status().isBadRequest());
    }


}
