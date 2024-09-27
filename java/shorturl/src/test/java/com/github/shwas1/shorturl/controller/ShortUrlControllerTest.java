package com.github.shwas1.shorturl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shwas1.shorturl.ShortUrlApplicationTests;
import com.github.shwas1.shorturl.model.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ShortUrlControllerTest extends ShortUrlApplicationTests {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shortUrl() throws Exception {
        String originalUrl = "https://www.baidu.com/";
        MvcResult generateShortUrlMvcResult = mockMvc.perform(get("/generateShortUrl").param("originalUrl", originalUrl))
                .andExpect(status().isOk()).andReturn();
        Result generateShortUrlResult = objectMapper.readValue(generateShortUrlMvcResult.getResponse().getContentAsString(), Result.class);

        assertNotNull(generateShortUrlResult);
        assertEquals(generateShortUrlResult.getSuccess(), true);
        Object shortUrl = generateShortUrlResult.getData();
        assertNotNull(shortUrl);

        MvcResult revertOriginalUrlMvcResult = mockMvc.perform(get("/revertOriginalUrl").param("shortUrl", shortUrl.toString()))
                .andExpect(status().isOk()).andReturn();
        Result revertOriginalUrlResult = objectMapper.readValue(revertOriginalUrlMvcResult.getResponse().getContentAsString(), Result.class);

        assertNotNull(revertOriginalUrlResult);
        assertEquals(revertOriginalUrlResult.getSuccess(), true);
        assertEquals(revertOriginalUrlResult.getData(), originalUrl);
    }

}